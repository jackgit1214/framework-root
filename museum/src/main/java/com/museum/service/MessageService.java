package com.museum.service;

import java.util.List;

import com.framework.mybatis.service.IBusinessService;
import com.framework.mybatis.util.PageResult;
import com.museum.model.MessageInbox;
import com.museum.model.MessageOutbox;

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
			StringBuffer msgBoxId);

	/**
	 * 根据用户ID，取得用户的未读消息
	 * 
	 * @param userId
	 * @return
	 */
	public List<MessageInbox> getUserMessage(String userId);

	/**
	 * 返回指定用户的所有消息，包含已读及未读， 并按照已读状态、时间进行排序
	 * 
	 * @param queryModel
	 * @param page
	 * @return
	 */
	public PageResult<MessageInbox> getUserMessage(String userid,
			String searchContent, PageResult<MessageInbox> page);

	/**
	 * 返回指定用户的草稿消息
	 * 
	 * @param queryModel
	 * @param page
	 * @return
	 */
	public PageResult<MessageOutbox> getUserDraftMessage(String userid,
			String searchContent, PageResult<MessageOutbox> page);

	/**
	 * 返回指定用户的已发送消息
	 * 
	 * @param queryModel
	 * @param page
	 * @return
	 */
	public PageResult<MessageOutbox> getUserSendMessage(String userid,
			String searchContent, PageResult<MessageOutbox> page);

	/**
	 * 返回指定用户的已删除
	 * 
	 * @param queryModel
	 * @param page
	 * @return
	 */
	public PageResult<MessageInbox> getUserDelMessage(String userid,
			String searchContent, PageResult<MessageInbox> page);

	/**
	 * 根据消息类型，返回指定用户的收到的消息
	 * 
	 * @param queryModel
	 * @param page
	 * @return
	 */
	public PageResult<MessageInbox> getUserMessage(String userid, String type,
			String searchContent, PageResult<MessageInbox> page);

	void getMessage(String userId, String searchContent, PageResult page,
			String type);

}