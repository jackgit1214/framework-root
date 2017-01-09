package com.system.platform;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.framework.web.controller.BaseController;
import com.framework.web.util.SessionManager;
import com.system.model.SysUser;
import com.system.mybatis.service.ISystemUserService;

@Controller
@RequestMapping("/")
@SessionAttributes(types = SessionManager.class)
public class SystemIndex extends BaseController {

	@Autowired
	private ISystemUserService systemUserServiceImpl;
	
	@RequestMapping("/loginSuccess")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("index");
		SysUser user = (SysUser)this.getSessionUser();
		if (user != null) {
			this.sessionManager.setUser(user);
			//this.systemUserServiceImpl.getUserModule(user, null);
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
	public ModelMap checkUserPasword(String loginid, String password,HttpServletRequest request) {
		boolean isSuccess = false;
		ModelMap modelMap = new ModelMap();
		SysUser user = this.systemUserServiceImpl.checkUserLogin(loginid,password);
		if (user != null) {
			SessionManager sessionManager = new SessionManager();
			sessionManager.setUser(user);
			isSuccess = true;
			request.getSession().setAttribute("sessionManager",sessionManager);
		}
		modelMap.addAttribute("isSuccess", isSuccess);
		return modelMap;
	}	
	
	/**
	 * 检测用户密码是否合法
	 * @param loginid
	 * @param password
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/passwordValid")
	public boolean passwordValid(String userid, String password,HttpServletRequest request) {
		boolean isSuccess = true;
		SysUser user = this.systemUserServiceImpl.checkUserLogin(userid,password);
		if (user != null) {
			isSuccess = true;
		}
		return isSuccess;
	}	
	
}
