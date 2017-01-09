package com.system.platform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.framework.web.controller.BaseController;
import com.framework.web.util.SessionManager;
import com.system.model.SysModule;
import com.system.model.SysUser;
import com.system.mybatis.service.ISysModuleService;
import com.system.mybatis.service.ISystemUserService;

@Controller
@RequestMapping("/system")
@SessionAttributes(types = SessionManager.class)
public class SystemManageController extends BaseController {

	@Autowired
	private ISystemUserService systemUserServiceImpl;

	
	@Autowired
	@Qualifier("sysModuleServiceImpl")
	private ISysModuleService sysModuleService;
	
	
	@RequestMapping("/index")
	public ModelAndView index(String id) {
		
		List<SysModule> modules = null;
		SysUser user = (SysUser)this.getSessionUser();
		modules = this.sysModuleService.findMenusByUser(user, id);
		ModelAndView mav = new ModelAndView("system/index");
		mav.addObject("modules",modules);
		return mav;
	}

}
