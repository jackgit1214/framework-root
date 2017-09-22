package com.museum.service;

import java.util.List;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.AppraisalExpertIdea;

public interface AppraisalExpertIdeaService extends
		IBusinessService<AppraisalExpertIdea> {
	int delete(String recordId);

	int delete(String[] recordIds);

	int deleteByDataId(String id);

	int save(AppraisalExpertIdea record);

	public List<AppraisalExpertIdea> getExpertIdeaByBussId(String id);

}