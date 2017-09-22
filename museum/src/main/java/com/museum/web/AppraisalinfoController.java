package com.museum.web;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.framework.common.anaotation.DuplicateSubmission;
import com.framework.common.binding.anaotation.FormList;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.framework.web.controller.BaseController;
import com.museum.common.aop.NeedCode;
import com.museum.model.AppraisalExpertIdea;
import com.museum.model.Appraisalinfo;
import com.museum.service.AppraisalExpertIdeaService;
import com.museum.service.AppraisalinfoService;
import com.museum.service.CommCodeService;
import com.system.common.SysConstant;
import com.system.mybatis.service.ISysCodeService;

@Controller
@RequestMapping("/appraisalinfo")
public class AppraisalinfoController extends BaseController {

	@Autowired
	private AppraisalinfoService appraisalinfoServiceImpl;

	@Autowired
	private AppraisalExpertIdeaService appraisalExpertIdeaServiceImpl;

	@Autowired
	private ISysCodeService sysCodeService;

	@Autowired
	private CommCodeService commCodeServiceImpl;

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("appraisalinfo/listindex");
		return mav;
	}

	@NeedCode(codeType = { "cpjb" }, commCodeType = { "coll_info-isAppraisal",
			"appraisalinfoApprType" })
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
		PageResult<Appraisalinfo> page = new PageResult<Appraisalinfo>(pageNo,
				pageNum);
		try {
			this.appraisalinfoServiceImpl.findObjectsByPage(queryModel, page);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView("appraisalinfo/listdata");
		mav.addObject("param", JSON.toJSONString(queryModel));
		mav.addObject("page", page);
		return mav;
	}

	@RequestMapping("/showEdit")
	@DuplicateSubmission(needSaveToken = true)
	public ModelAndView showEdit(String id) {

		ModelAndView mav = new ModelAndView("appraisalinfo/edit");
		Appraisalinfo appraisalinfo = this.appraisalinfoServiceImpl
				.findObjectById(id);
		if (appraisalinfo == null) {
			appraisalinfo = new Appraisalinfo();
			appraisalinfo.setApplicationdate(Calendar.getInstance().getTime());
			appraisalinfo.setUpdatingDate(Calendar.getInstance().getTime()
					.toString());
		} else {
			List<AppraisalExpertIdea> expertIdeas = this.appraisalExpertIdeaServiceImpl
					.getExpertIdeaByBussId(id);
			mav.addObject("expertIdeas", expertIdeas);
		}
		mav.addObject("appraisalinfo", appraisalinfo);
		return mav;
	}

	@ResponseBody
	@RequestMapping("/update")
	@DuplicateSubmission(needRemoveToken = true)
	public ModelMap addOrUpdate(Appraisalinfo record,
			@FormList("expertIdeas") List<AppraisalExpertIdea> expertIdeas,
			String delIds) throws Exception {
		ModelMap modelMap = new ModelMap();
		int rows = this.appraisalinfoServiceImpl.save(record, expertIdeas,
				delIds);

		if (rows <= 0)
			modelMap.addAttribute("isSuccess", false);

		modelMap.addAttribute("successRows", rows);
		modelMap.addAttribute("data", record);

		return modelMap;

	}

	@ResponseBody
	@RequestMapping("/delete")
	public ModelMap delete(
			@RequestParam(value = "ids[]", required = false) String[] ids) {
		ModelMap modelMap = new ModelMap();
		int rows = this.appraisalinfoServiceImpl.delete(ids);
		modelMap.addAttribute("successRows", rows);
		return modelMap;
	}
}