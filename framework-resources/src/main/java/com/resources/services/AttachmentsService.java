package com.resources.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.framework.mybatis.service.IBusinessService;
import com.resources.model.CommAttachments;

public interface AttachmentsService extends IBusinessService<CommAttachments> {

	public String docExt = "txt|md|csv|nfo|ini|json|php|js|css|doc|docx";
	public String videoExt = "ogg|mp4|mp?g|mov|webm|3gp";
	public String audioExt = "ogg|mp3|mp?g|wav";
	public String imageExt = "gif|png|jp?g|";

	int delete(String recordId);

	Map<String, Integer> delete(String[] recordIds);

	int save(CommAttachments record);

	public List<String> attachmentHandle(HttpServletRequest request,
			CommAttachments record);

	public byte[] getResource(String dataid, String permission);

	public String getResource(String dataid);
}