package com.massivecraft.massivecore.store;

import com.massivecraft.massivecore.MassiveCoreMConf;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.store.subscriber.ObservableSubscriber;
import com.massivecraft.massivecore.xlib.bson.Document;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.mongodb.ConnectionString;
import com.massivecraft.massivecore.xlib.mongodb.client.model.ReplaceOptions;
import com.massivecraft.massivecore.xlib.mongodb.client.result.DeleteResult;
import com.massivecraft.massivecore.xlib.mongodb.client.result.UpdateResult;
import com.massivecraft.massivecore.xlib.mongodb.reactivestreams.client.MongoClient;
import com.massivecraft.massivecore.xlib.mongodb.MongoNamespace;
import com.massivecraft.massivecore.xlib.mongodb.reactivestreams.client.MongoClients;
import com.massivecraft.massivecore.xlib.mongodb.reactivestreams.client.MongoCollection;
import com.massivecraft.massivecore.xlib.mongodb.reactivestreams.client.MongoDatabase;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DriverMongoAsync extends DriverAbstract
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
	
	protected static DriverMongoAsync i = new DriverMongoAsync();
	public static DriverMongoAsync get() { return i; }
	DriverMongoAsync() { super("asyncmongodb"); }
	
	// -------------------------------------------- //
	// IMPLEMENTATION
	// -------------------------------------------- //
	
	@Override
	public Db getDb(String uri)
	{
		MongoDatabase db = this.getDbInner(uri);
		return new DbMongoAsync(this, db);
	}
	
	@Override
	public boolean dropDb(Db db)
	{
		// If the db is a mongo db...
		if (!(db instanceof DbMongoAsync)) throw new IllegalArgumentException("db");
		
		// Cast to mongo db...
		DbMongoAsync dbMongo = (DbMongoAsync) db;
		
		try
		{
			// Subscribe and await...
			ObservableSubscriber subscriber = new ObservableSubscriber();
			dbMongo.db.drop().subscribe(subscriber);
			if (subscriber.getError() != null) throw subscriber.getError();
			subscriber.await();
			
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
		// Subscribe and get...
		ObservableSubscriber<String> subscriber = new ObservableSubscriber<>();
		((DbMongoAsync) db).db.listCollectionNames().subscribe(subscriber);
		if (subscriber.getError() != null) throw subscriber.getError();
		Set<String> collNames = new HashSet<>(subscriber.get());
		
		// Build set...
		Set<String> ret = new HashSet<>(collNames);
		
		// Filter...
		ret.remove("system.indexes");
		ret.remove("system.users");
		
		// Return
		return ret;
	}
	
	@Override
	public boolean renameColl(Db db, String from, String to)
	{
		// If the document to be renamed exists...
		if (!this.getCollnames(db).contains(from)) return false;
		
		// And the new namespace doesnt exist...
		if (this.getCollnames(db).contains(to)) return false;
		
		// Get the database...
		MongoDatabase mdb = ((DbMongoAsync) db).db;
		
		// Subscribe and rename...
		ObservableSubscriber subscriber = new ObservableSubscriber();
		mdb.getCollection(from).renameCollection(new MongoNamespace(mdb.getName(), to)).subscribe(subscriber);
		if (subscriber.getError() != null) throw subscriber.getError();
		subscriber.await();
		
		return true;
	}
	
	@Override
	public boolean containsId(Coll<?> coll, String id)
	{
		// Get the collection...
		MongoCollection<Document> dbcoll = fixColl(coll);
		
		// Subscribe and find...
		ObservableSubscriber<Document> subscriber = new ObservableSubscriber<>();
		dbcoll.find(new Document(ID_FIELD, id)).first().subscribe(subscriber);
		if (subscriber.getError() != null) throw subscriber.getError();
		List<Document> documents = subscriber.get();
		
		// If it isnt 0, must contain the document
		return documents.size() != 0;
	}
	
	@Override
	public long getMtime(Coll<?> coll, String id)
	{
		// Get the collection...
		MongoCollection<Document> dbcoll = fixColl(coll);
		
		// Subscribe and find...
		ObservableSubscriber<Document> subscriber = new ObservableSubscriber<>();
		dbcoll.find(new Document(ID_FIELD, id)).projection(dboKeysMtime).first().subscribe(subscriber);
		if (subscriber.getError() != null) throw subscriber.getError();
		List<Document> documents = subscriber.get();
		
		// If doesnt = 0...
		if (documents.size() == 0) return 0;
		
		// Grab the document...
		Document found = documents.get(0);
		
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
		
		// Grab the collection...
		MongoCollection<Document> dbcoll = fixColl(coll);
		
		// Subscribe and find...
		ObservableSubscriber<Document> subscriber = new ObservableSubscriber<>();
		dbcoll.find().projection(dboKeysId).subscribe(subscriber);
		if (subscriber.getError() != null) throw subscriber.getError();
		List<Document> found = subscriber.get();
		
		// Get the ids of the documents...
		ret = new ArrayList<>(found.size());
		for (Document doc : found) {
			ret.add(doc.get(ID_FIELD).toString());
		}
		
		// And return them
		return ret;
	}
	
	@Override
	public Map<String, Long> getId2mtime(Coll<?> coll)
	{
		Map<String, Long> ret;
		
		// Get the collection...
		MongoCollection<Document> dbcoll = fixColl(coll);
		
		// Subscribe and find...
		ObservableSubscriber<Document> subscriber = new ObservableSubscriber<>();
		dbcoll.find().projection(dboKeysIdandMtime).subscribe(subscriber);
		if (subscriber.getError() != null) throw subscriber.getError();
		List<Document> found = subscriber.get();
		
		ret = new HashMap<>(found.size());
		
		// Get the document id and mtime...
		for (Document doc : found) {
			Object remoteId = doc.get(ID_FIELD);
			Object mtime = doc.get(MTIME_FIELD);
			
			mtime = cleanMtime(mtime);
			
			// In case there is no _mtime set we assume 1337.
			// NOTE: We can not use 0 since that one is reserved for errors.
			// Probably a manual database addition by the server owner.
			//Long mtime = raw.getLong(MTIME_FIELD);
			
			ret.put(remoteId.toString(), mtime == null ? 1337L : (Long)mtime);
		}
		
		// And return them
		return ret;
	}
	
	@Override
	public Entry<JsonObject, Long> load(Coll<?> coll, String id)
	{
		// Get the collection...
		MongoCollection<Document> dbcoll = fixColl(coll);
		
		// Subscribe and find...
		ObservableSubscriber<Document> subscriber = new ObservableSubscriber<>();
		dbcoll.find(new Document(ID_FIELD, id)).first().subscribe(subscriber);
		if (subscriber.getError() != null) throw subscriber.getError();
		if (subscriber.get().size() == 0) return null;
		Document raw = subscriber.get().get(0);
		
		// Return the raw JSON entry
		return loadRaw(raw);
	}
	
	@Override
	public Map<String, Entry<JsonObject, Long>> loadAll(Coll<?> coll)
	{
		// Declare Ret
		Map<String, Entry<JsonObject, Long>> ret;
		
		// Get the collection...
		MongoCollection<Document> dbcoll = fixColl(coll);
		
		// Subscribe and find...
		ObservableSubscriber<Document> subscriber = new ObservableSubscriber<>();
		dbcoll.find().subscribe(subscriber);
		if (subscriber.getError() != null) throw subscriber.getError();
		List<Document> found = subscriber.get();
		
		// Create Ret
		ret = new MassiveMap<>(found.size());
		
		// For Each Found
		for (Document doc : found) {
			
			// Get ID
			Object rawId = doc.remove(ID_FIELD);
			if (rawId == null) continue;
			String id = rawId.toString();
			
			// Get Entry
			Entry<JsonObject, Long> entry = loadRaw(doc);
			// NOTE: The entry can be a failed one with null and 0.
			// NOTE: We add it anyways since it's an informative failure.
			// NOTE: This is supported by our defined specification.
			
			// Add
			ret.put(id, entry);
		}
		
		// Return Ret
		return ret;
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
	public long save(Coll<?> coll, String id, JsonObject data)
	{
		// Get the collection...
		MongoCollection<Document> dbcoll = fixColl(coll);
		
		// Write concern...
		dbcoll = dbcoll.withWriteConcern(MassiveCoreMConf.get().getMongoDbWriteConcernSave());
		
		// Get the new document...
		Document doc = new Document();
		
		// Set last alter and id...
		long mtime = System.currentTimeMillis();
		doc.put(ID_FIELD, id);
		doc.put(MTIME_FIELD, mtime);
		
		// Convert from GSON to mongo...
		GsonMongoConverter.gson2MongoObject(data, doc);
		
		// Subscibe and replace...
		ObservableSubscriber<UpdateResult> subscriber = new ObservableSubscriber<>();
		dbcoll.replaceOne(new Document(ID_FIELD, id), doc, replaceOptions).subscribe(subscriber);
		if (subscriber.getError() != null) throw subscriber.getError();
		subscriber.await();
		
		// Return the mtime
		return mtime;
	}
	
	@Override
	public void delete(Coll<?> coll, String id)
	{
		// Get the collection...
		MongoCollection<Document> dbcoll = fixColl(coll);
		
		// Write concern...
		dbcoll = dbcoll.withWriteConcern(MassiveCoreMConf.get().getMongoDbWriteConcernDelete());
		
		// Subscribe and delete
		ObservableSubscriber<DeleteResult> subscriber = new ObservableSubscriber<>();
		dbcoll.deleteOne(new Document(ID_FIELD, id)).subscribe(subscriber);
		if (subscriber.getError() != null) throw subscriber.getError();
		subscriber.await();
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
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	protected static MongoCollection<Document> fixColl(Coll<?> coll)
	{
		return (MongoCollection<Document>) coll.getCollDriverObject();
	}
	
	protected MongoDatabase getDbInner(String uri)
	{
		ConnectionString muri = new ConnectionString(uri);
		
		try
		{
			// TODO: Create one of these per collection? Really? Perhaps I should cache.
			MongoClient mongoClient = MongoClients.create(muri);
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
