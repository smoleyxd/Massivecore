package com.massivecraft.massivecore.store;

import com.massivecraft.massivecore.MassiveCoreMConf;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.xlib.bson.Document;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.mongodb.MongoClient;
import com.massivecraft.massivecore.xlib.mongodb.MongoClientURI;
import com.massivecraft.massivecore.xlib.mongodb.MongoNamespace;
import com.massivecraft.massivecore.xlib.mongodb.client.FindIterable;
import com.massivecraft.massivecore.xlib.mongodb.client.MongoCollection;
import com.massivecraft.massivecore.xlib.mongodb.client.MongoDatabase;
import com.massivecraft.massivecore.xlib.mongodb.client.model.ReplaceOptions;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DriverMongo extends DriverAbstract
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	public final static String ID_FIELD = "_id";
	public final static String MTIME_FIELD = "_mtime";
	
	public final static Document dboKeysId = new Document().append(ID_FIELD, 1);
	public final static Document dboKeysMtime = new Document().append(MTIME_FIELD, 1);
	public final static Document dboKeysIdandMtime = new Document().append(ID_FIELD, 1).append(MTIME_FIELD, 1);
	
	public final static ReplaceOptions replaceOptions = new ReplaceOptions().upsert(true);
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	protected static DriverMongo i = new DriverMongo();
	public static DriverMongo get() { return i; }
	DriverMongo() { super("mongodb"); }
	
	// -------------------------------------------- //
	// IMPLEMENTATION
	// -------------------------------------------- //
	
	@Override
	public Db getDb(String uri)
	{
		MongoDatabase db = this.getDbInner(uri);
		return new DbMongo(this, db);
	}
	
	@Override
	public boolean dropDb(Db db)
	{
		if (!(db instanceof DbMongo)) throw new IllegalArgumentException("db");
		DbMongo dbMongo = (DbMongo) db;
		
		try
		{
			dbMongo.db.drop();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	@Override
	public Set<String> getCollnames(Db db)
	{
		Set<String> ret = new HashSet<>();
		
		for (String col : ((DbMongo) db).db.listCollectionNames())
		{
			ret.add(col);
		}
		
		ret.remove("system.indexes");
		ret.remove("system.users");
		return ret;
	}
	
	@Override
	public boolean renameColl(Db db, String from, String to)
	{
		if (!this.getCollnames(db).contains(from)) return false;
		if (this.getCollnames(db).contains(to)) return false;
		
		MongoDatabase mdb = ((DbMongo) db).db;
		mdb.getCollection(from).renameCollection(new MongoNamespace(mdb.getName(), to));
		
		return true;
	}
	
	@Override
	public boolean containsId(Coll<?> coll, String id)
	{
		MongoCollection dbcoll = fixColl(coll);
		return dbcoll.find(new Document(ID_FIELD, id)).first() != null;
	}
	
	@Override
	public long getMtime(Coll<?> coll, String id)
	{
		MongoCollection dbcoll = fixColl(coll);
		
		Document found = (Document) dbcoll.find(new Document(ID_FIELD, id)).projection(dboKeysMtime).first();
		if (found == null) return 0;
		
		// In case there is no _mtime set we assume 1337.
		// NOTE: We can not use 0 since that one is reserved for errors.
		// Probably a manual database addition by the server owner.
		Object mtime = found.get(MTIME_FIELD);
		
		mtime = cleanMtime(mtime);
		
		return mtime == null ? 1337L : (Long) mtime;
	}
	
	private Object cleanMtime(Object mtime)
	{
		if (! (mtime instanceof Long) && mtime != null) {
			if (mtime instanceof Integer) {
				mtime = ((Integer) mtime).longValue();
			}
			else {
				System.out.println("ERROR - Invalid mtime");
				mtime = null;
			}
		}
		return mtime;
	}
	
	@Override
	public Collection<String> getIds(Coll<?> coll)
	{
		List<String> ret;
		
		MongoCollection dbcoll = fixColl(coll);
		
		FindIterable found = dbcoll.find().projection(dboKeysId);
		
		ret = new ArrayList<>((int) dbcoll.countDocuments());
		
		for (Object doc : found) {
			ret.add(((Document)doc).get(ID_FIELD).toString());
		}
		
		return ret;
	}
	
	@Override
	public Map<String, Long> getId2mtime(Coll<?> coll)
	{
		Map<String, Long> ret;
		
		MongoCollection dbcoll = fixColl(coll);
		
		FindIterable found = dbcoll.find().projection(dboKeysIdandMtime);
		
		ret = new HashMap<>((int)dbcoll.countDocuments());
		
		for (Object doc : found) {
			Document raw = (Document) doc;
			Object remoteId = raw.get(ID_FIELD);
			Object mtime = raw.get(MTIME_FIELD);
			
			mtime = cleanMtime(mtime);
			
			// In case there is no _mtime set we assume 1337.
			// NOTE: We can not use 0 since that one is reserved for errors.
			// Probably a manual database addition by the server owner.
			//Long mtime = raw.getLong(MTIME_FIELD);
			
			ret.put(remoteId.toString(), mtime == null ? 1337L : (Long)mtime);
		}
		
		return ret;
	}
	
	@Override
	public Entry<JsonObject, Long> load(Coll<?> coll, String id)
	{
		MongoCollection dbcoll = fixColl(coll);
		Document raw = (Document) dbcoll.find(new Document(ID_FIELD, id)).first();
		return loadRaw(raw);
	}
	
	public Entry<JsonObject, Long> loadRaw(Document raw)
	{
		if (raw == null) return new SimpleEntry<>(null, 0L);
		
		// Throw away the id field
		raw.remove(ID_FIELD);
		
		// In case there is no _mtime set we assume 1337.
		// NOTE: We can not use 0 since that one is reserved for errors.
		// Probably a manual database addition by the server owner.
		long mtime = 1337L;
		Object mtimeObject = raw.remove(MTIME_FIELD);
		if (mtimeObject != null)
		{
			mtime = ((Number)mtimeObject).longValue();
		}
		
		// Convert MongoDB --> GSON
		JsonObject element = GsonMongoConverter.mongo2GsonObject(raw);
		
		return new SimpleEntry<>(element, mtime);
	}
	
	@Override
	public Map<String, Entry<JsonObject, Long>> loadAll(Coll<?> coll)
	{
		// Declare Ret
		Map<String, Entry<JsonObject, Long>> ret;
		
		// Fix Coll
		MongoCollection dbcoll = fixColl(coll);
		
		// Find All
		FindIterable found = dbcoll.find();
		
		// Create Ret
		ret = new MassiveMap<>((int) dbcoll.countDocuments());
		
		// For Each Found
		for (Object doc : found) {
			Document raw = (Document) doc;
			
			// Get ID
			Object rawId = raw.remove(ID_FIELD);
			if (rawId == null) continue;
			String id = rawId.toString();
			
			// Get Entry
			Entry<JsonObject, Long> entry = loadRaw(raw);
			// NOTE: The entry can be a failed one with null and 0.
			// NOTE: We add it anyways since it's an informative failure.
			// NOTE: This is supported by our defined specification.
			
			// Add
			ret.put(id, entry);
		}
		
		// Return Ret
		return ret;
	}
	
	@Override
	public long save(Coll<?> coll, String id, JsonObject data)
	{
		MongoCollection<Document> dbcoll = fixColl(coll);
		
		dbcoll = dbcoll.withWriteConcern(MassiveCoreMConf.get().getMongoDbWriteConcernSave());
		
		Document doc = new Document();
		
		long mtime = System.currentTimeMillis();
		doc.put(ID_FIELD, id);
		doc.put(MTIME_FIELD, mtime);
		
		GsonMongoConverter.gson2MongoObject(data, doc);
		
		dbcoll.replaceOne(new Document(ID_FIELD, id), doc, replaceOptions);
		
		return mtime;
	}
	
	@Override
	public void delete(Coll<?> coll, String id)
	{
		MongoCollection dbcoll = fixColl(coll);
		dbcoll = dbcoll.withWriteConcern(MassiveCoreMConf.get().getMongoDbWriteConcernDelete());
		
		dbcoll.deleteOne(new Document(ID_FIELD, id));
	}
	
	@Override
	public boolean supportsPusher()
	{
		return false;
	}
	
	@Override
	public PusherColl getPusher(Coll<?> coll)
	{
		throw new UnsupportedOperationException("MongoDB does not have a pusher change.");
	}
	
	
	//----------------------------------------------//
	// UTIL
	//----------------------------------------------//
	
	protected static MongoCollection fixColl(@NotNull Coll<?> coll)
	{
		return (MongoCollection) coll.getCollDriverObject();
	}
	
	protected MongoDatabase getDbInner(String uri)
	{
		MongoClientURI muri = new MongoClientURI(uri);
		
		try
		{
			// TODO: Create one of these per collection? Really? Perhaps I should cache.
			MongoClient mongoClient = new MongoClient(muri);
			
			MongoDatabase db = mongoClient.getDatabase(muri.getDatabase());
			
			if (muri.getUsername() == null) return db;

			return db;
		}
		catch (Exception e)
		{
			//log(Level.SEVERE, "... mongo connection failed.");
			e.printStackTrace();
			return null;
		}
	}

}
