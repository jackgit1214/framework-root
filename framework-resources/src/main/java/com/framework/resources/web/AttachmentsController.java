package com.framework.resources.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.framework.resources.model.CommAttachments;
import com.framework.resources.services.AttachmentsService;
import com.framework.web.controller.BaseController;
import com.system.common.SysConstant;

@Controller
@RequestMapping("/resources")
public class AttachmentsController extends BaseController {

	@Autowired
	private AttachmentsService attachmentsServiceImpl;

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}

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
		PageResult<CommAttachments> page = new PageResult<CommAttachments>(
				pageNo, pageNum);
		try {
			this.attachmentsServiceImpl.findObjectsByPage(queryModel, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("commattachments/listdata");
		mav.addObject("param", JSON.toJSONString(queryModel));
		mav.addObject("page", page);
		return mav;
	}

	@ResponseBody
	@RequestMapping("/getAttaData")
	public ModelMap getDataList(QueryModel queryModel, Integer pageNo,
			Integer pageNum) {
		ModelMap modelMap = new ModelMap();
		queryModel.reInitCriteria();
		if (pageNum == null || pageNum == 0) {
			pageNum = SysConstant.SYSDEFAULTROWNUM;
		}
		if (pageNo == null || pageNo == 1) {
			pageNo = 1;
		}
		PageResult<CommAttachments> page = new PageResult<CommAttachments>(
				pageNo, pageNum);
		try {
			this.attachmentsServiceImpl.findObjectsByPage(queryModel, page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		modelMap.addAttribute("attachments", page.getPageDatas());
		return modelMap;
	}

	@RequestMapping("/showEdit")
	public ModelAndView showEdit(String id) {
		CommAttachments commAttachments = this.attachmentsServiceImpl
				.findObjectById(id);
		if (commAttachments == null)
			commAttachments = new CommAttachments();
		ModelAndView mav = new ModelAndView("commattachments/edit");
		mav.addObject("commAttachments", commAttachments);
		return mav;
	}

	@ResponseBody
	@RequestMapping("/update")
	public ModelMap addOrUpdate(CommAttachments record,
			HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();

		List<String> attIds = this.attachmentsServiceImpl.attachmentHandle(
				request, record);
		modelMap.addAttribute("successRows", attIds.size());
		modelMap.addAttribute("data", record);
		return modelMap;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public ModelMap delete(
			@RequestParam(value = "ids[]", required = false) String[] ids) {
		ModelMap modelMap = new ModelMap();

		Map<String, Integer> rtnMap = this.attachmentsServiceImpl.delete(ids);
		modelMap.addAllAttributes(rtnMap);

		return modelMap;
	}
}