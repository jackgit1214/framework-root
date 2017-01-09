package com.museum.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.web.controller.BaseController;
import com.system.model.SysModule;
import com.system.model.SysUser;
import com.system.mybatis.service.ISysModuleService;

@Controller
@RequestMapping("/func")
public class FuncMenuController extends BaseController {

	
	@Autowired
	@Qualifier("sysModuleServiceImpl")
	private ISysModuleService sysModuleService;
	
	/**
	 * 取得菜单
	 * @return
	 */
	@RequestMapping("/getFunction")
	@ResponseBody
	public List<SysModule> getSysMainModule(String parentId){
		List<SysModule> modules = null;
		SysUser user = (SysUser)this.getSessionUser();
		
		modules = this.sysModuleService.findMenusByUser(user, parentId);
		return modules;
	}
	
	
}
