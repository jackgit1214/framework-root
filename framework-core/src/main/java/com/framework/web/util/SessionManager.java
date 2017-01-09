package com.framework.web.util;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class SessionManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private SessionUser user;
	
	private Date loginDate; //登入时间
	private Date logoutDate; //登出时间
	
	public SessionUser  getUser() {
		return user;
	}

	public void setUser(SessionUser  user) {
		this.user = user;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}
	
	
}
