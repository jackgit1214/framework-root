package com.museum.service;

import java.util.Map;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.CollInfo;

public interface CollInfoService extends IBusinessService<CollInfo> {
	int delete(String recordId);

	int delete(String[] recordIds);

	int save(CollInfo record);

	public Map<String, Map<String, Integer>> getCollNum(int type, int condition);

	public Map<String, Map<String, Integer>> culTypeStat(int year);
}