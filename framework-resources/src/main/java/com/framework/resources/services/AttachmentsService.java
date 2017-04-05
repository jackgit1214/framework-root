package com.framework.resources.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.framework.mybatis.service.IBusinessService;
import com.framework.resources.model.CommAttachments;

public interface AttachmentsService extends IBusinessService<CommAttachments> {
	int delete(String recordId);

	Map<String, Integer> delete(String[] recordIds);

	int save(CommAttachments record);

	public List<String> attachmentHandle(HttpServletRequest request,
			CommAttachments record);
}