package com.framework.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.framework.common.converter.CustomDoubleEditor;
import com.framework.common.converter.CustomFloatEditor;
import com.framework.common.converter.CustomIntegerEditor;
import com.framework.common.converter.CustomLongEditor;
import com.framework.common.converter.CustomShortEditor;
import com.framework.common.converter.SpecCustomBooleanEditor;
import com.framework.web.util.SessionManager;
import com.framework.web.util.SessionUser;

public abstract class BaseController {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	protected SessionManager sessionManager;

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) {
		//PropertyEditorRegistrySupport
		// 注册int,float,long,double类型转换
		binder.registerCustomEditor(int.class,new CustomIntegerEditor() );
		binder.registerCustomEditor(float.class,new CustomFloatEditor() );
		binder.registerCustomEditor(double.class,new CustomDoubleEditor() );
		binder.registerCustomEditor(long.class,new CustomLongEditor() );
		binder.registerCustomEditor(short.class,new CustomShortEditor() );
		
		//注册boolean类型转换，缺省值为false
		binder.registerCustomEditor(boolean.class,new SpecCustomBooleanEditor(false) );
		
		// 注册日期转换
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	protected HttpServletRequest getRequest() {

		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		return sra.getRequest();
	}

	/** 根据错误代码值，取得信息 */
	protected String getMessageSourceInfo(String codekey) {
		WebApplicationContext ac = RequestContextUtils
				.getWebApplicationContext(this.getRequest());
		String messageinfo = ac.getMessage(codekey, null, this.getRequest()
				.getLocale());
		return messageinfo;
	}


	protected void getSessionManager(HttpServletRequest request) {
		this.sessionManager = (SessionManager) request.getSession()
				.getAttribute("sessionManager");

	}

	protected SessionUser getSessionUser() {

		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		this.sessionManager = (SessionManager) req.getSession().getAttribute("sessionManager");
		if (this.sessionManager == null)
			return null;
		return (SessionUser) this.sessionManager.getUser();
	}
}
