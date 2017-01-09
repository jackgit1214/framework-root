package com.system.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.framework.web.controller.BaseController;
import com.system.common.SysConstant;
import com.system.model.SysRole;
import com.system.mybatis.service.ISystemRoleService;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

	@Autowired
	private ISystemRoleService systemRoleServiceImpl;

	/**
	 * 显示用户列表数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		int pageNum = SysConstant.SYSDEFAULTROWNUM;
		PageResult<SysRole> page = new PageResult<SysRole>(1, pageNum);
		try {
			this.systemRoleServiceImpl.findObjectsByPage(null, page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("system/roles/listindex");

		mav.addObject("page", page);
		return mav;
	}

	/**
	 * 显示用户列表数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView dataList(QueryModel queryModel, Integer pageNo,
			Integer pageNum) {

		if (pageNum == null || pageNum == 0) {
			pageNum = SysConstant.SYSDEFAULTROWNUM;
		}
		if (pageNo == null || pageNo == 1) {
			pageNo = 1;
		}
		queryModel.reInitCriteria();
		PageResult<SysRole> page = new PageResult<SysRole>(pageNo, pageNum);
		try {
			this.systemRoleServiceImpl.findObjectsByPage(queryModel, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("system/roles/listdata");
		log.debug(JSON.toJSONString(queryModel));
		mav.addObject("param", JSON.toJSONString(queryModel));
		mav.addObject("page", page);
		return mav;
	}

	/**
	 * 显示新增或编辑界面
	 * 
	 * @param userid
	 *            userid为空时显示的是新增界面，否则显示的是编辑界面。
	 * @return
	 */
	@RequestMapping("/showEditRole")
	public ModelAndView showEditUserWin(String roleid) {
		ModelAndView mav = new ModelAndView("system/roles/editrole");
		SysRole role = this.systemRoleServiceImpl.findObjectById(roleid);

		if (role == null)
			role = new SysRole();

		Object jsonNode = this.systemRoleServiceImpl.findRoleModule(role);

		mav.addObject("role", role);

		mav.addObject("treeModules", jsonNode.toString());
		return mav;
	}

	/**
	 * 新增或保存编辑数据
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/updateRole")
	public ModelMap addOrUpdateRole(SysRole role, String[] modids) {
		ModelMap mm = new ModelMap();
		
		//与前端接口，目前有问题，不能收数组
		//先分离下
		String[] ids={};
		if (modids.length >0 )
			ids = modids[0].split(",");
		int rows = this.systemRoleServiceImpl.saveRolePermission(role, ids);
		mm.addAttribute("successRows", rows);
		return mm;
	}

	/**
	 * 删除角色数据
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/deleteRole")
	public ModelMap deleteRole(
			@RequestParam(value = "ids[]", required = false) String[] ids) {
		ModelMap mm = new ModelMap();
		int rows = this.systemRoleServiceImpl.delete(ids);
		mm.addAttribute("successRows", rows);
		return mm;
	}

}
