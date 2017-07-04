package com.museum.system;

import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.framework.common.anaotation.LinkHistoryAnaotation;
import com.framework.web.controller.BaseController;
import com.framework.web.util.SessionManager;
import com.system.common.SysConstant;
import com.system.model.SysUser;
import com.system.mybatis.service.ISystemUserService;

@Controller
@RequestMapping("/")
@SessionAttributes(types = SessionManager.class)
public class SystemIndex extends BaseController {

	@Autowired
	private ISystemUserService systemUserServiceImpl;

	@RequestMapping("/loginSuccess")
	@LinkHistoryAnaotation(linkLevel = SysConstant.INDEX_SIGN, linkName = "首页", linkValue = "/index")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("index");
		SysUser user = (SysUser) this.getSessionUser();
		if (user != null) {
			this.sessionManager.clearHistory();
			this.sessionManager.setUser(user);
			// this.systemUserServiceImpl.getUserModule(user, null);
			mav.addObject("user", user);
		} else {
			request.getSession().invalidate();
			mav.setViewName("redirect:/index");
		}
		return mav;
	}

	@RequestMapping("/index")
	public ModelAndView index(String loginid, String password) {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}

	@ResponseBody
	@RequestMapping("/checkUserPassword")
	public ModelMap checkUserPasword(String loginid, String password,
			HttpServletRequest request) {
		boolean isSuccess = false;
		ModelMap modelMap = new ModelMap();
		HttpSession session = request.getSession(true);

		if (session != null) {
			// session清空，登录成功后重新启动session ，避免会话标识未更新的安全错误(appscan扫描结果）
			session.invalidate();
			Cookie cookie = request.getCookies()[0];// 获取cookie
			cookie.setMaxAge(0);// 让cookie过期
		}

		SysUser user = this.systemUserServiceImpl.checkUserLogin(loginid,
				password);

		if (user != null) {
			SessionManager sessionManager = new SessionManager();
			sessionManager.setUser(user);
			sessionManager.setLoginDate(Calendar.getInstance().getTime());
			isSuccess = true;
			request.getSession().setAttribute("sessionManager", sessionManager);
		}
		modelMap.addAttribute("isSuccess", isSuccess);
		return modelMap;
	}

	/**
	 * 检测用户密码是否合法
	 * 
	 * @param loginid
	 * @param password
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/passwordValid")
	public boolean passwordValid(String userid, String password,
			HttpServletRequest request) {
		boolean isSuccess = true;
		SysUser user = this.systemUserServiceImpl.checkUserLogin(userid,
				password);
		if (user != null) {
			isSuccess = true;
		}
		return isSuccess;
	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("index");
		request.getSession().invalidate();
		mav.setViewName("redirect:/index");

		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "putHistory", method = { RequestMethod.POST })
	public ModelMap putHistory(String name, String link,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		ModelMap mm = new ModelMap();
		SessionManager sessionManager = (SessionManager) session
				.getAttribute("sessionManager");
		if (sessionManager != null) {
			this.sessionManager.setHistory(SysConstant.SECOUND_LINK_SIGN, name,
					link);
		}
		mm.addAttribute("history", this.sessionManager.getHistory());

		return mm;
	}
}
