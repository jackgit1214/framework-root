package com.framework.mybatis.service;

import java.util.List;

import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;

public interface IBusinessService<T> {

	public List<T> findAllObjects();

	public T findObjectById(Object object);

	public List<T> findObjects(QueryModel queryModel);

	public PageResult<T> findObjectsByPage(QueryModel queryModel,
			PageResult<T> page) throws Exception;

	/**
	 * 取得指定序列的下一个值
	 * 
	 * @param sequenceName
	 * @return
	 */
	public int getNextVal(String sequenceName);

	/**
	 * 取得指定序列的当前值
	 * 
	 * @param sequenceName
	 * @return
	 */
	public int getCurVal(String sequenceName);

}
