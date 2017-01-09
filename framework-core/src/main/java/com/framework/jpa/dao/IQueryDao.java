/**
 * 
 */
package com.framework.jpa.dao;


import java.util.List;

import com.framework.jpa.dao.queryutil.IQueryProperty;
import com.framework.model.BaseModel;


/**
 * @author lilj
 * 
 */
public interface IQueryDao<T extends BaseModel> {


	public abstract T findById(Object objectId);

	public abstract T findById(Object objectId,Class<T> clasz);	
	
	/**
	 * 具有延迟加载作用，待验证
	 * @param entityClass
	 * @param entityId
	 * @return
	 */
	public abstract T load(Object entityId);

	public abstract T load(Object entityId,Class<T> clasz);
	
	/**
	 * 取得实体类总行数
	 * @param entityClass
	 * @return
	 */
	public abstract int getTotalCount();

	/**
	 * 取得行数
	 * @param entityClass
	 * @return
	 */
	public abstract int getQueryCount(IQueryProperty queryProperty);
	
	
	/**
	 * 根据某个实体属性查询数据
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return
	 */
	public abstract List<T> findByProperty(String propertyName,
			Object value, int... rowStartIdxAndCount);

	/**
	 * 根据一个属性组进行查询
	 */
	public abstract List<T> findByProperty(IQueryProperty queryProperty, int...rowStartIdxAndCount);

	/**
	 * 根据一个属性组进行查询
	 */
	public abstract List<T> findByProperty(IQueryProperty queryProperty,IQueryProperty orderPro, int...rowStartIdxAndCount);
	
	
	/**
	 * 根据查询jpql语句进行查询
	 * 返回对象，查询列必须有相应字段的构造方法
	 * 必须为针对相应实体的查询
	 */
	public abstract List<T> queryResult(String jpql,int ...rowStartIdxAndCount) throws Exception;
	
	
	/**
	 * 针对sql语句的查询，
	 * @param sql sql语句串
	 * @param rowStartIdxAndCount 查询行数
	 * @return
	 */
	public abstract List<T> queryNativeResult(String sql,Class<?> classz,String fields,int ...rowStartIdxAndCount)  throws Exception ;
	
	/**
	 * 加载所有实体数据
	 * @param entityClass
	 * @param rowStartIdxAndCount
	 * @return
	 */
	public abstract List<T> findAll(int... rowStartIdxAndCount);

	
}