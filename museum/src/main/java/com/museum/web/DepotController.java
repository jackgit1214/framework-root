package com.museum.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.framework.web.controller.BaseController;
import com.museum.model.SysDepot;
import com.museum.model.SysDepotTree;
import com.museum.service.ISysDepotService;
import com.system.common.SysConstant;

@Controller
@RequestMapping("/system/depot")
public class DepotController extends BaseController {

	@Autowired
	private ISysDepotService sysDepotServiceImpl;

	/**
	 * 显示用户列表数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index() {

		List<SysDepotTree> depotTree = this.sysDepotServiceImpl
				.getDepotBySupperId("ABC");
		ModelAndView mav = new ModelAndView("system/depot/listindex");
		mav.addObject("depotsTree", JSON.toJSON(depotTree).toString());

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
		PageResult<SysDepot> page = new PageResult<SysDepot>(pageNo, pageNum);

		try {
			this.sysDepotServiceImpl.findObjectsByPage(queryModel, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("system/depot/listdata");
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
	@RequestMapping("/showEdit")
	public ModelAndView showEdit(String id, SysDepotTree node) {
		ModelAndView mav = new ModelAndView("system/depot/edit");
		SysDepot sysDepot = this.sysDepotServiceImpl.findObjectById(id);

		if (sysDepot == null)
			sysDepot = new SysDepot();

		sysDepot.setSuperName(node.getDepotName());
		sysDepot.setSuperid(node.getDepotId());
		sysDepot.setDutyMan(node.getDutyMan());
		mav.addObject("sysDepot", sysDepot);
		return mav;
	}

	/**
	 * 新增或保存编辑数据
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/update")
	public ModelMap addOrUpdate(SysDepot record) {
		ModelMap mm = new ModelMap();

		int rows = this.sysDepotServiceImpl.updateDepot(record);
		mm.addAttribute("successRows", rows);
		mm.addAttribute("data", record);
		return mm;
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/delete")
	public ModelMap delete(
			@RequestParam(value = "ids[]", required = false) String[] ids) {
		ModelMap mm = new ModelMap();
		int rows = this.sysDepotServiceImpl.delete(ids);
		mm.addAttribute("successRows", rows);
		return mm;
	}

	@ResponseBody
	@RequestMapping(value = "/getDepotdata", method = RequestMethod.POST)
	public List<SysDepotTree> getDepotData(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "pId", required = false) String pId) {
		List<SysDepotTree> depots = this.sysDepotServiceImpl
				.getDepotBySupperId(id);
		return depots;
	}
}
