package com.museum.web.websock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.museum.MuseumConstant;
import com.museum.model.MsgBody;
import com.museum.service.MessageService;

public class SystemWebSocketHandler implements WebSocketHandler {

	private Logger log = LoggerFactory.getLogger(SystemWebSocketHandler.class);

	@Autowired
	private MessageService messageServiceImpl;

	private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();;

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {

		log.debug("ConnectionEstablished");
		users.add(session);

	}

	@Override
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {

		log.debug("handleMessage" + message.toString());

		ObjectMapper mapper = new ObjectMapper();
		MsgBody msgBody = mapper.readValue(message.getPayload().toString(),
				MsgBody.class);

		sendMessages(msgBody);

	}

	// 后台错误信息处理方法
	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {

		if (session.isOpen()) {
			session.close();
		}
		users.remove(session);
		log.debug("handleTransportError" + exception.getMessage());
	}

	/**
	 * 用户退出后的处理，不如退出之后，要将用户信息从websocket的session中remove掉，
	 * 
	 * 这样用户就处于离线状态了，也不会占用系统资源
	 */

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		users.remove(session);

	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 发送消息
	 * 
	 * @param msgBody
	 */
	public void sendMessages(MsgBody msgBody) {
		// TODO Auto-generated method stub
		String toUser = msgBody.getToUser();
		TextMessage message = new TextMessage(msgBody.getContent());
		if (toUser == null || "".equals(toUser) || "ALL".equals(toUser)) {

			this.sendMessagesToUsers(message, msgBody.getFromUser());
		} else {
			this.sendMessageToUser(toUser, message, msgBody.getFromUser());
		}
	}

	/**
	 * 给所有的用户发送消息
	 */
	public void sendMessagesToUsers(TextMessage message, String fromUserId) {
		for (WebSocketSession user : users) {
			try {
				// isOpen()在线就发送
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送消息给指定的用户
	 */
	public void sendMessageToUser(String userIds, TextMessage message,
			String fromUserId) {

		StringBuffer id = new StringBuffer("");
		this.messageServiceImpl.save(userIds, message.getPayload(), true, id,
				fromUserId);
		List<String> userids = Arrays.asList(userIds.split(","));
		for (WebSocketSession user : users) {
			String tmpid = (String) user.getAttributes().get(
					MuseumConstant.CURRENT_WEBSOCKET_USER);
			if (userids.contains(tmpid)) {
				try {
					if (user.isOpen()) {
						user.sendMessage(message);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}