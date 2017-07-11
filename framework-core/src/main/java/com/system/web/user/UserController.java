package com.system.web.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.framework.web.controller.BaseController;
import com.system.common.SysConstant;
import com.system.model.SysRole;
import com.system.model.SysUser;
import com.system.mybatis.service.ISystemUserService;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

	@Autowired
	private ISystemUserService systemUserServiceImpl;

	/**
	 * 显示用户列表数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		int pageNum = SysConstant.SYSDEFAULTROWNUM;
		PageResult<SysUser> page = new PageResult<SysUser>(1, pageNum);
		try {
			this.systemUserServiceImpl.findObjectsByPage(null, page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("system/user/listindex");

		mav.addObject("page", page);
		return mav;
	}

	/**
	 * 显示用户列表数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView dataList(QueryModel queryModel, Integer pageNo,
			Integer pageNum) {

		if (pageNum == null || pageNum == 0) {
			pageNum = SysConstant.SYSDEFAULTROWNUM;
		}

		if (pageNo == null || pageNo == 1) {
			pageNo = 1;
		}
		queryModel.reInitCriteria();
		PageResult<SysUser> page = new PageResult<SysUser>(pageNo, pageNum);
		try {
			this.systemUserServiceImpl.findObjectsByPage(queryModel, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("system/user/listdata");

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
	@RequestMapping("/showEditUser")
	public ModelAndView showEditUserWin(String userid) {
		ModelAndView mav = new ModelAndView("system/user/editUser");
		SysUser user = this.systemUserServiceImpl.findObjectById(userid);

		if (user == null)
			user = new SysUser();
		List<SysRole> sysRoles = this.systemUserServiceImpl.getUserRole(user);

		mav.addObject("user", user);
		mav.addObject("roles", sysRoles);
		return mav;
	}

	/**
	 * 显示新增或编辑界面
	 * 
	 * @param userid
	 *            userid为空时显示的是新增界面，否则显示的是编辑界面。
	 * @return
	 */
	@RequestMapping("/view/{userid}")
	public ModelAndView viewUser(@PathVariable String userid) {

		ModelAndView mav = new ModelAndView("system/user/viewUser");
		SysUser user = this.systemUserServiceImpl.findObjectById(userid);
		List<SysRole> sysRoles = this.systemUserServiceImpl.getUserRole(user);
		mav.addObject("roles", sysRoles);
		mav.addObject("user", user);

		return mav;
	}

	/**
	 * 新增或保存编辑数据
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/updateUser")
	public ModelMap addOrUpdateUser(SysUser user, String[] roleids) {
		ModelMap mm = new ModelMap();
		int rows = this.systemUserServiceImpl.saveUser(user, roleids);
		mm.addAttribute("successRows", rows);
		return mm;
	}

	@ResponseBody
	@RequestMapping("/updateAvatar")
	public ModelMap updateUserWithAvatar(SysUser user,
			HttpServletRequest request) {
		ModelMap mm = new ModelMap();
		int rows = this.systemUserServiceImpl.updateUser(user, request);

		mm.addAttribute("successRows", rows);
		return mm;
	}

	@RequestMapping(value = "image/{userId}", method = RequestMethod.GET)
	public void getImageResource(@PathVariable String userId,
			HttpServletResponse response) {

		ServletOutputStream output = null;
		InputStream in = null;
		byte[] avatar = this.systemUserServiceImpl.getUserAvatar(userId);
		if (avatar != null) {

			try {
				output = response.getOutputStream();
				output.write(avatar, 0, avatar.length);
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String path = this.getClass().getResource("/").getPath();
			String zwtp = path + "/config/zwtp.gif";
			File file = new File(zwtp);
			byte[] tempbytes = new byte[1024];
			int byteread = 0;
			try {
				in = new FileInputStream(file);
				output = response.getOutputStream();

				while ((byteread = in.read(tempbytes)) != -1) {
					output.write(tempbytes, 0, byteread);
				}
				output.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 新增或保存编辑数据
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/deleteUser")
	public ModelMap deleteUser(
			@RequestParam(value = "ids[]", required = false) String[] ids) {
		ModelMap mm = new ModelMap();

		int rows = this.systemUserServiceImpl.delete(ids);
		mm.addAttribute("successRows", rows);
		return mm;

	}
}
