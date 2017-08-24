package com.museum.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.model.QueryModel.Criteria;
import com.framework.mybatis.service.AbstractBusinessService;
import com.framework.mybatis.util.PageResult;
import com.framework.web.util.SessionManager;
import com.museum.MuseumConstant;
import com.museum.dao.MessageInboxMapper;
import com.museum.dao.MessageOutboxMapper;
import com.museum.model.MessageInbox;
import com.museum.model.MessageOutbox;
import com.museum.service.MessageService;
import com.system.model.SysUser;

@Service
@Transactional
public class MessageServiceImpl extends AbstractBusinessService<MessageInbox>
		implements MessageService {

	private final String msgAcceptPath = MuseumConstant.WEBSOCK_STOMP_CLIENT_ACCEPT_PATH_PREFIXES
			+ MuseumConstant.WEBSOCK_STOMP_CLIENT_ACCEPT_MSGNUM_PATH;

	@Autowired
	private MessageInboxMapper messageInboxMapper;

	@Autowired
	private MessageOutboxMapper messageOutboxMapper;

	// 用于转发数据，发送给客户
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	public MessageServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
		super();
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	public BaseDao getDao() {
		return this.messageInboxMapper;
	}

	public int delete(String recordId) {
		int rows = this.messageInboxMapper.deleteByPrimaryKey(recordId);
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int delete(String[] recordIds, String curType) {
		int rows = 0;
		// QueryModel queryModel = new QueryModel();

		for (String id : recordIds) {

			if ("del".equals(curType)) // 真删除
				rows = rows + this.messageInboxMapper.deleteByPrimaryKey(id);
			else if ("draft".equals(curType)) {
				rows = rows + this.messageOutboxMapper.deleteByPrimaryKey(id);
			} else if ("send".equals(curType)) {
				rows = rows + this.messageOutboxMapper.deleteByPrimaryKey(id);
			} else { // 逻辑删除

				MessageInbox record = this.messageInboxMapper
						.selectByPrimaryKey(id);
				String userid = record.getToUserId();
				String dest = this.msgAcceptPath + "/" + userid;
				record.setIsDel(1);
				rows = rows
						+ this.messageInboxMapper.updateByPrimaryKey(record);
				// 逻辑删除，即删除消息，消息数量减少
				if (record.getIsRead() == 0) // 未读消息删除时才发送订阅消息数量
					this.simpMessagingTemplate.convertAndSend(dest,
							MuseumConstant.CHANGETYPE.DEL);
			}
		}
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int save(MessageInbox record) {
		int rows = 0;
		if (record.getId() == null || record.getId() == "") {
			String uuid = UUIDUtil.getUUID();
			record.setId(uuid);
			rows = this.messageInboxMapper.insert(record);
		} else {
			rows = this.messageInboxMapper.updateByPrimaryKey(record);
		}
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int save(String userids, String content, boolean isSend,
			StringBuffer msgBoxId, String fromUserId) {
		int rows = 0;
		if (isSend) {
			// 发送消息，写入收件箱
			rows = this.sendMsg(userids, content, fromUserId);
		}
		// 更改发件内容
		String id = this.updateMsgOutbox(msgBoxId.toString(), userids, content,
				isSend, fromUserId);
		if (!"".equals(id)) {
			msgBoxId.setLength(0);
			msgBoxId.append(id);
			rows = rows + 1;
		}

		this.logger.debug("rows: {}", rows);
		return rows;

	}

	private String updateMsgOutbox(String msgBoxId, String userids,
			String content, boolean isSend, String fromUserId) {

		int isRead = 0;
		if (isSend)
			isRead = 1;

		if (fromUserId == null || "".equals(fromUserId))
			fromUserId = this.getSessionUser().getUserid();
		// 去除最后的分隔符
		// String tmpUserid = userids.substring(0, userids.length() - 1);
		String tmpUserid = userids;
		if (msgBoxId == null || "".equals(msgBoxId)) {
			String uuid = UUIDUtil.getUUID();
			MessageOutbox record = new MessageOutbox();
			msgBoxId = uuid;
			record.setId(uuid);
			record.setContent(content);
			record.setCreateDate(Calendar.getInstance().getTime());
			record.setFromUserId(fromUserId);
			record.setToUserId(tmpUserid);

			record.setIsRead(isRead);
			this.messageOutboxMapper.insert(record);
		} else {
			MessageOutbox record = this.messageOutboxMapper
					.selectByPrimaryKey(msgBoxId);
			record.setContent(content);
			record.setToUserId(tmpUserid);
			record.setIsRead(isRead);
			record.setCreateDate(Calendar.getInstance().getTime());
			this.messageOutboxMapper.updateByPrimaryKey(record);
		}
		return msgBoxId;
	}

	/**
	 * 发送消息,向收件箱插入数据
	 * 
	 * @param userid
	 * @param content
	 * @return
	 */
	private int sendMsg(String userid, String content, String fromUserId) {
		int rows = 0;
		MessageInbox record = new MessageInbox();

		if (fromUserId == null || "".equals(fromUserId))
			fromUserId = this.getSessionUser().getUserid();

		String[] users = userid.split(",");
		for (String user : users) {

			// 发送给指定用户的消息路径,也是客户端的接收路径
			String dest = msgAcceptPath + "/" + user;
			String uuid = UUIDUtil.getUUID();
			record.setId(uuid);
			record.setCreateDate(Calendar.getInstance().getTime());
			record.setContent(content);
			record.setFromUserId(fromUserId);
			record.setIsRead(0); // 默认为未读
			record.setToUserId(user);
			this.simpMessagingTemplate.convertAndSend(dest,
					MuseumConstant.CHANGETYPE.ADD);
			rows = rows + this.messageInboxMapper.insert(record);
		}
		return rows;
	}

	private SysUser getSessionUser() {

		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		this.log.debug(req);
		SessionManager session = (SessionManager) req.getSession()
				.getAttribute("sessionManager");
		if (session == null)
			return null;
		return (SysUser) session.getUser();
	}

	@Override
	public List<MessageInbox> getUserMessage(String userId) {

		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria().andEqualTo("to_user_id", userId)
				.andEqualTo("is_read", "0").andEqualTo("isdel", 0);
		queryModel.setOrderByClause("is_Read Asc, create_date desc ");
		List<MessageInbox> messages = this.messageInboxMapper
				.selectByConditionJoinUser(queryModel);
		return messages;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getMessage(String userId, String searchContent,
			PageResult page, String type) {
		if ("accept".equals(type)) { // 收件箱数据
			this.getUserMessage(userId, searchContent, page);
		} else if ("send".equals(type)) { // 发件箱数据

			this.getUserSendMessage(userId, searchContent, page);

		} else if ("draft".equals(type)) { // 草稿箱数据
			this.getUserDraftMessage(userId, searchContent, page);

		} else if ("del".equals(type)) { // 删除的数据
			this.getUserDelMessage(userId, searchContent, page);
		} else if ("0".equals(type)) { // 用户发送的消息
			this.getUserMessage(userId, type, searchContent, page);
		} else if ("1".equals(type)) {
			this.getUserMessage(userId, type, searchContent, page);
		}

	}

	/**
	 * 返回指定用户的所有消息，包含已读及未读， 并按照已读状态、时间进行排序
	 * 
	 * @param queryModel
	 * @param page
	 * @return
	 */
	private PageResult<MessageInbox> getUserMessage(String userId,
			String searchContent, PageResult<MessageInbox> page) {

		QueryModel queryModel = new QueryModel();
		Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("to_user_id", userId).andEqualTo("isdel", 0)
				.andNotEqualTo("is_read", 2);

		if (searchContent != null && !"".equals(searchContent)) {
			criteria.andLike("content", "%" + searchContent + "%");
		}
		queryModel.setOrderByClause("is_Read Asc, create_date Desc ");
		List<MessageInbox> messages = this.messageInboxMapper
				.selectByConditionJoinUser(queryModel, page);
		page.setPageDatas(messages);
		return page;
	}

	/**
	 * 返回指定用户的草稿消息
	 * 
	 * @param queryModel
	 * @param page
	 * @return
	 */
	private PageResult<MessageOutbox> getUserDraftMessage(String userid,
			String searchContent, PageResult<MessageOutbox> page) {

		QueryModel queryModel = new QueryModel();
		Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("from_user_id", userid).andEqualTo("is_read", 0);

		if (searchContent != null && !"".equals(searchContent)) {
			criteria.andLike("content", "%" + searchContent + "%");
		}
		queryModel.setOrderByClause("create_date Desc ");
		List<MessageOutbox> messages = this.messageOutboxMapper
				.selectByConditionJoinUser(queryModel, page);
		// for (MessageOutbox message : messages) {
		// String userids = message.getToUserId();
		//
		// }
		page.setPageDatas(messages);
		return page;

	}

	/**
	 * 返回指定用户的已发送消息
	 * 
	 * @param queryModel
	 * @param page
	 * @return
	 */
	private PageResult<MessageOutbox> getUserSendMessage(String userid,
			String searchContent, PageResult<MessageOutbox> page) {

		QueryModel queryModel = new QueryModel();
		Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("from_user_id", userid).andEqualTo("is_read", 1);

		if (searchContent != null && !"".equals(searchContent)) {
			criteria.andLike("content", "%" + searchContent + "%");
		}
		queryModel.setOrderByClause("create_date Desc ");
		List<MessageOutbox> messages = this.messageOutboxMapper
				.selectByConditionJoinUser(queryModel, page);

		page.setPageDatas(messages);
		return page;
	}

	/**
	 * 返回指定用户的已删除
	 * 
	 * @param queryModel
	 * @param page
	 * @return
	 */
	private PageResult<MessageInbox> getUserDelMessage(String userid,
			String searchContent, PageResult<MessageInbox> page) {
		QueryModel queryModel = new QueryModel();
		Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("to_user_id", userid).andEqualTo("isDel", 1);

		if (searchContent != null && !"".equals(searchContent)) {
			criteria.andLike("content", "%" + searchContent + "%");
		}
		queryModel.setOrderByClause("create_date Desc ");
		List<MessageInbox> messages = this.messageInboxMapper
				.selectByConditionJoinUser(queryModel, page);
		page.setPageDatas(messages);
		return page;
	}

	/**
	 * 根据消息类型，返回指定用户的收到的消息
	 * 
	 * @param queryModel
	 * @param page
	 * @return
	 */
	private PageResult<MessageInbox> getUserMessage(String userid,
			String msgType, String searchContent, PageResult<MessageInbox> page) {

		QueryModel queryModel = new QueryModel();
		Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("to_user_id", userid).andEqualTo("type", msgType);

		if (searchContent != null && !"".equals(searchContent)) {
			criteria.andLike("content", "%" + searchContent + "%");
		}
		queryModel.setOrderByClause("is_Read Asc, create_date Desc ");
		List<MessageInbox> messages = this.messageInboxMapper
				.selectByConditionJoinUser(queryModel, page);
		page.setPageDatas(messages);
		return page;
	}

	/**
	 * 取得两个用户的对话消息记录
	 * 
	 * @param fromUserId
	 * @param toUserid
	 * @param page
	 * @param searchContent
	 * @return
	 */
	@Override
	public PageResult<MessageInbox> getMsgByFromUserAndToUser(
			String fromUserId, String toUserid, PageResult<MessageInbox> page,
			String searchContent) {
		// TODO Auto-generated method stub

		QueryModel queryModel = new QueryModel();
		Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("to_user_id", toUserid).andEqualTo("from_user_id",
				fromUserId);

		queryModel.or().andEqualTo("to_user_id", fromUserId)
				.andEqualTo("from_user_id", toUserid);

		if (searchContent != null && !"".equals(searchContent)) {
			criteria.andLike("content", "%" + searchContent + "%");
		}

		queryModel.setOrderByClause("create_date Desc ");

		List<MessageInbox> messages = this.messageInboxMapper
				.selectByConditionJoinUser(queryModel, page);
		SysUser user = this.getSessionUser();
		for (MessageInbox msgbox : messages) {
			if (msgbox.getIsRead() == 0
					&& msgbox.getToUserId().equals(user.getUserid())) { // 只有是接收的消息
																		// ，置为已读
				msgbox.setIsRead(1);

				this.messageInboxMapper.updateByPrimaryKeySelective(msgbox);// 更新为已读
				String dest = this.msgAcceptPath + "/" + msgbox.getToUserId();
				this.simpMessagingTemplate.convertAndSend(dest,
						MuseumConstant.CHANGETYPE.DEL);
			}
		}
		page.setPageDatas(messages);
		return page;
	}
}