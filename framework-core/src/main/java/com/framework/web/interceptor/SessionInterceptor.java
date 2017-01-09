/**
 * 
 */
package com.framework.web.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.framework.web.util.SessionManager;
import com.framework.web.util.WebUtils;


public class SessionInterceptor extends HandlerInterceptorAdapter {

	private final Log log = LogFactory.getLog(this.getClass());
	
	private final String FIRSTPAGE="/";
	
	/**
	 * 在控制类调用之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest _request,
			HttpServletResponse _response, Object _handler) throws Exception {
		// TODO Auto-generated method stub

		//log.info(_request.getRequestURI());
		if (_request.getSession()==null){
			log.error("session error");
			return turnLogin(_response);
		}else{
			HttpSession session = _request.getSession();
			SessionManager sessionManager = (SessionManager)session.getAttribute("sessionManager");
			if (sessionManager==null){
				return turnLogin(_response);
			}
		}
		return true;
		
	}
	
	/**
	 * 返回登录
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private boolean turnLogin(HttpServletResponse response) throws IOException{
		WebUtils.alertAndGo("会话超时，请重登录 ",FIRSTPAGE, response);
		return false;	
	}
	
}
