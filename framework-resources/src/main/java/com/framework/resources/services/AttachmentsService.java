package com.framework.resources.services;

import com.framework.mybatis.service.IBusinessService;
import com.framework.resources.model.CommAttachments;

public interface AttachmentsService extends
		IBusinessService<CommAttachments> {
	int delete(String recordId);

	int delete(String[] recordIds);

	int save(CommAttachments record);
}