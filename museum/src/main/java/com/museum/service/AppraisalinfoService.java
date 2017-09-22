package com.museum.service;

import java.util.List;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.AppraisalExpertIdea;
import com.museum.model.Appraisalinfo;

public interface AppraisalinfoService extends IBusinessService<Appraisalinfo> {

	public String sequenceName = "seq_Appraisal";

	int delete(String recordId);

	int delete(String[] recordIds);

	int save(Appraisalinfo record);

	int save(Appraisalinfo record, List<AppraisalExpertIdea> expertIdeas,
			String delIds);
}