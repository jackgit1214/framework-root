package com.framework.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.framework.common.anaotation.DuplicateSubmission;
import com.framework.common.util.RandomGUID;
import com.framework.web.util.SessionManager;

/**
 * 
 * @author lilj 防止重复提交过滤器
 */
public class DuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {

	private final Log log = LogFactory.getLog(this.getClass());

	private String curToken;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();
		SessionManager sessionManager = (SessionManager) session
				.getAttribute("sessionManager");
		if (sessionManager != null) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			DuplicateSubmission annotation = method
					.getAnnotation(DuplicateSubmission.class);
			if (annotation != null) {
				boolean needSaveSession = annotation.needSaveToken();
				if (needSaveSession) {
					RandomGUID token = new RandomGUID();
					request.getSession(false).setAttribute("token",
							token.toString());
				}

				boolean needRemoveSession = annotation.needRemoveToken();
				if (needRemoveSession) {
					if (isRepeatSubmit(request)) {
						log.warn("请不要重复提交,[用户:"
								+ sessionManager.getUser().toString() + ",url:"
								+ request.getServletPath() + "]");
						return false;
					}
					// request.getSession(false).removeAttribute("token");
				}
			}
		}

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		if (ex != null) {
			request.getSession(false).setAttribute("token", this.curToken);
		}
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		DuplicateSubmission annotation = method
				.getAnnotation(DuplicateSubmission.class);
		if (annotation != null) {
			boolean needRemoveSession = annotation.needRemoveToken();
			if (needRemoveSession) {
				Object obj = request.getAttribute("isSuccess");
				if (obj != null) {
					boolean isSuccess = (boolean) request
							.getAttribute("isSuccess");
					if (isSuccess)
						request.getSession(false).removeAttribute("token");
				}
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(false).getAttribute(
				"token");
		if (serverToken == null) {
			return true;
		}
		this.curToken = serverToken;
		String clinetToken = request.getParameter("token");
		if (clinetToken == null) { // 如果前段没有token值，则可以提交
			return false;
		}
		if (!serverToken.equals(clinetToken)) {
			return true;
		}
		return false;
	}
}
