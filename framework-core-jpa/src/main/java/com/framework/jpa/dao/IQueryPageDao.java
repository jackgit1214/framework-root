/**
 * 
 */
package com.framework.jpa.dao;


import com.framework.jpa.dao.queryutil.IQueryProperty;
import com.framework.model.BaseModel;
import com.framework.web.page.PageResult;


/**
 * @author lilj
 * 
 */
public interface IQueryPageDao<T extends BaseModel> extends ICommonDao<T> {
	
	public abstract PageResult findByProperty(String propertyName, Object value,int pageNum);

	public abstract PageResult findByProperty(IQueryProperty queryProperty,int pageNum);
	
	public abstract PageResult findByProperty(IQueryProperty queryProperty,IQueryProperty orderPro,int pageNum);
	
	public abstract PageResult queryResult(String jpql,int pageNum) throws Exception;
	
	public abstract PageResult queryNativeResult(String sql,Class<?> classz,String fields,int pageNum) throws Exception ;
	
	public abstract PageResult findAll(int pageNum);

	/**
	 * 配置每页记录数
	 * @param pageRowNum
	 */
	public abstract void setPageRowNum(int pageRowNum);
	
}