package com.framework.mybatis.service;

import org.springframework.stereotype.Service;


@Service
public interface ICommonService<T> {

	public int saveObject(T object);
	
	public int delete(String[] ids);

	public int delete(String id);
	
}
