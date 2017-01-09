package com.framework.mybatis.service;

import java.util.List;

import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;


public interface IBusinessService<T> {
	
	public List<T> findAllObjects();
	
	public T findObjectById(Object object);
	
	public List<T> findObjects(QueryModel queryModel);
	
	public PageResult<T> findObjectsByPage(QueryModel queryModel,PageResult<T> page) throws Exception;
	
	
}
