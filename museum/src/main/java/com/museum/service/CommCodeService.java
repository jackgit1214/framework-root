package com.museum.service;

import java.util.List;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.CommCode;

public interface CommCodeService extends IBusinessService<CommCode> {
	int delete(String recordId);

	int delete(String[] recordIds);

	int save(CommCode record);

	List<CommCode> getCommCodes(String table, String field);

	List<CommCode> getCommCodes(String[] codeTypes);

}