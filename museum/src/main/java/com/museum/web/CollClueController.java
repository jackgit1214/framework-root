package com.museum.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.framework.common.anaotation.DuplicateSubmission;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.framework.web.controller.BaseController;
import com.museum.model.CollClue;
import com.museum.service.CollClueService;
import com.system.common.SysConstant;

@Controller
@RequestMapping("/collclue")
public class CollClueController extends BaseController {
	@Autowired
	private CollClueService collClueServiceImpl;

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("coll/collclue/listindex");
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
		PageResult<CollClue> page = new PageResult<CollClue>(pageNo, pageNum);
		try {
			this.collClueServiceImpl.findObjectsByPage(queryModel, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("coll/collclue/listdata");
		mav.addObject("param", JSON.toJSONString(queryModel));
		mav.addObject("page", page);
		return mav;
	}

	@RequestMapping("/showEdit")
	@DuplicateSubmission(needSaveToken = true)
	public ModelAndView showEdit(String id) {
		CollClue collClue = this.collClueServiceImpl.findObjectById(id);
		if (collClue == null)
			collClue = new CollClue();
		ModelAndView mav = new ModelAndView("coll/collclue/edit");
		mav.addObject("collClue", collClue);
		return mav;
	}

	@ResponseBody
	@RequestMapping("/update")
	@DuplicateSubmission(needRemoveToken = true)
	public ModelMap addOrUpdate(CollClue record) {

		ModelMap modelMap = new ModelMap();
		int rows = this.collClueServiceImpl.save(record);
		modelMap.addAttribute("successRows", rows);
		modelMap.addAttribute("data", record);
		return modelMap;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public ModelMap delete(
			@RequestParam(value = "ids[]", required = false) String[] ids) {
		ModelMap modelMap = new ModelMap();
		int rows = this.collClueServiceImpl.delete(ids);
		modelMap.addAttribute("successRows", rows);
		return modelMap;
	}
}