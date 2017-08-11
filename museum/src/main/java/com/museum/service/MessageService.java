package com.museum.service;

import java.util.List;

import com.framework.mybatis.service.IBusinessService;
import com.framework.mybatis.util.PageResult;
import com.museum.model.MessageInbox;

public interface MessageService extends IBusinessService<MessageInbox> {

	int delete(String recordId);

	int delete(String[] recordIds, String curType);

	int save(MessageInbox record);

	/**
	 * 
	 * @param userids
	 * @param content
	 * @param isSend
	 * @return
	 */
	int save(String userids, String content, boolean isSend,
			StringBuffer msgBoxId, String fromUserId);

	/**
	 * 根据用户ID，取得用户的未读消息
	 * 
	 * @param userId
	 * @return
	 */
	public List<MessageInbox> getUserMessage(String userId);

	/**
	 * 取得两个用户的对话消息记录
	 * 
	 * @param fromUserId
	 * @param toUserid
	 * @param page
	 * @param searchContent
	 * @return
	 */
	public PageResult<MessageInbox> getMsgByFromUserAndToUser(
			String fromUserId, String toUserid, PageResult<MessageInbox> page,
			String searchContent);

	@SuppressWarnings("rawtypes")
	public void getMessage(String userId, String searchContent,
			PageResult page, String type);

}