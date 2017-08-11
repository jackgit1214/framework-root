package com.museum.web.websock;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.framework.web.util.SessionManager;
import com.museum.MuseumConstant;
import com.system.model.SysUser;

public class WebSocketInterceptor implements HandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest _request,
			ServerHttpResponse serverHttpResponse,
			WebSocketHandler webSocketHandler, Exception exception) {
		// TODO Auto-generated method stub
		// HttpServletRequest req = ((ServletRequestAttributes)
		// RequestContextHolder
		// .getRequestAttributes()).getRequest();

	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandle,
			Map<String, Object> attributes) throws Exception {

		// 将ServerHttpRequest转换成request请求相关的类，用来获取request域中的用户信息
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpServletRequest httpRequest = servletRequest.getServletRequest();
			HttpSession session = httpRequest.getSession();
			SessionManager sessionManager = (SessionManager) session
					.getAttribute(MuseumConstant.CURRENT_SESSIONMANAGER);
			SysUser user = (SysUser) sessionManager.getUser();

			attributes.put(MuseumConstant.CURRENT_WEBSOCKET_USER,
					user.getUserid());
		}
		return true;
	}

}
