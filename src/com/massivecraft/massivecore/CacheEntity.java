package com.massivecraft.massivecore;

import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.ReflectionUtil;

public class CacheEntity<C extends Coll<E>, E extends Entity<E>>
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CacheEntity(Class<C> collClass)
	{
		this(collClass, null);
	}
	
	public CacheEntity(Class<C> collClass, String entityId)
	{
		this.collClass = collClass;
		this.entityId = entityId;
	}
	
	// -------------------------------------------- //
	// LOAD
	// -------------------------------------------- //
	
	public CacheEntity<C, E> load(CacheEntity<C, E> other)
	{
		// I don't think we would have a case in which we have reason to change Coll objects while in use
		this.entityId = other.entityId;
		return this;
	}
	
	// -------------------------------------------- //
	// COLL
	// -------------------------------------------- //
	
	private transient Class<C> collClass;
	public Class<C> getCollClass() { return this.collClass; }
	
	private transient Coll<E> coll = null;
	public Coll<E> getColl() { this.calculateColl(); return this.coll; }
	public void calculateColl()
	{
		if (coll != null) return;
		this.coll = ReflectionUtil.getSingletonInstance(this.getCollClass());
	}
	
	// -------------------------------------------- //
	// ENTITY ID
	// -------------------------------------------- //
	
	private String entityId;
	public String getEntityId() { return this.entityId; }
	
	/**
	 * Sets the entity id to track
	 * @param entityId the new id for the target entity
	 * @return true if the new id is different from the previous one, false otherwise
	 */
	public boolean setEntityId(String entityId)
	{
		if (MUtil.equals(this.entityId, entityId)) return false;
		this.entityId = entityId;
		return true;
	}
	
	// -------------------------------------------- //
	// ENTITY
	// -------------------------------------------- //
	
	private transient E cacheUsedEntity = null;
	
	public E getUsedEntity()
	{
		updateUsedEntityCache();
		return this.cacheUsedEntity;
	}
	
	/**
	 * Set the used entity for this Cache
	 * @param usedEntity the new entity to reference
	 * @return true if the new entity is different from the previous, false otherwise
	 */
	public boolean setUsedEntity(E usedEntity)
	{
		String oldId = this.entityId;
		String newId = null;
		if (usedEntity != null) newId = usedEntity.getId();
		
		// Detect no-change
		if (MUtil.equals(oldId, newId)) return false;
		
		this.entityId = newId;
		this.cacheUsedEntity = usedEntity;
		return true;
	}
	
	private void updateUsedEntityCache()
	{
		if (this.entityId == null)
		{
			this.cacheUsedEntity = null;
			return;
		}
		
		String cacheId = null;
		if (this.cacheUsedEntity != null) cacheId = this.cacheUsedEntity.getId();
		if (MUtil.equals(this.entityId, cacheId)) return;
		
		this.cacheUsedEntity = getColl().get(this.entityId);
	}
	
}
