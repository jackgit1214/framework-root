package com.resources.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.framework.mybatis.service.IBusinessService;
import com.resources.model.CommAttachments;

public interface AttachmentsService extends IBusinessService<CommAttachments> {

	public List<String> imageExtension = Arrays.asList(new String[] { "gif",
			"jpg", "jpeg", "png", "tif" });

	public List<String> fileType = Arrays.asList(new String[] { "image",
			"HTML", "text", "video", "audio", "flash", "PDF", "object" });

	int delete(String recordId);

	Map<String, Integer> delete(String[] recordIds);

	int save(CommAttachments record);

	public List<String> attachmentHandle(HttpServletRequest request,
			CommAttachments record);

	public byte[] getResource(String dataid, String permission);

	public String getResource(String dataid);
}