/**
 * 
 */
package com.framework.jpa.dao;

import javax.persistence.EntityManager;

import com.framework.model.BaseModel;

/**
 * @author lilj
 *
 */
public interface ICommonDao<T extends BaseModel> extends IPersistDao<T>, IQueryDao<T> {

	public abstract void setEntityName(Class<T> clasz,String entityName);
	
	public abstract EntityManager getEm();
}
