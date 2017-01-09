/**
 * 
 */
package com.framework.jpa.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.framework.model.BaseModel;

/**
 * @author lilj
 *
 */
public interface IPersistDao<T extends BaseModel> {

	
	public abstract  void save(T entity) throws DataAccessException;

	/**
	 * 批量保存实体
	 * @param entities
	 */
	public abstract  void batchSave(List<T> entities);
	
	public abstract void delete(T entity);

	/**
	 * 批量删除指定实体类
	 * @param entity
	 */
	public abstract void batchDelete(List<T> entity);
	
	/**
	 * 根据实体ID，批量删除数据
	 * @param classz
	 * @param objectes
	 */
	public abstract void batchDelete(Class<T> classz,Object[] objectes);

	
	
	public abstract Object update(T entity);
	/**
	 * 批量更新实体
	 * @param entities
	 * @return
	 */
	public abstract void batchUpdate(List<T> entities);
	
	
}
