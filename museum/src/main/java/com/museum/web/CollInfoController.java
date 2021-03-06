package com.museum.web;

import java.util.Calendar;

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
import com.museum.common.aop.NeedCode;
import com.museum.model.CollInfo;
import com.museum.service.CollInfoService;
import com.museum.service.CommCodeService;
import com.system.common.SysConstant;
import com.system.mybatis.service.ISysCodeService;

@Controller
@RequestMapping("/collinfo")
public class CollInfoController extends BaseController {
	@Autowired
	private CollInfoService collInfoServiceImpl;

	@Autowired
	private CommCodeService commCodeServiceImpl;

	@Autowired
	private ISysCodeService sysCodeService;

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("coll/collinfo/listindex");
		return mav;
	}

	@NeedCode(codeType = { "wwly", "zdlb" }, commCodeType = { "share-currency" })
	@RequestMapping("/list")
	public ModelAndView dataList(QueryModel queryModel, Integer pageNo,
			Integer pageNum) {
		ModelAndView mav = new ModelAndView("coll/collinfo/listview");
		if (pageNum == null || pageNum == 0) {
			pageNum = SysConstant.SYSDEFAULTROWNUM;
		}
		if (pageNo == null || pageNo == 1) {
			pageNo = 1;
		}
		queryModel.reInitCriteria();
		PageResult<CollInfo> page = new PageResult<CollInfo>(pageNo, pageNum);
		try {
			this.collInfoServiceImpl.findObjectsByPage(queryModel, page);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("param", JSON.toJSONString(queryModel));
		mav.addObject("page", page);
		return mav;
	}

	@RequestMapping("/showEdit")
	@DuplicateSubmission(needSaveToken = true)
	public ModelAndView showEdit(String id) {
		CollInfo collInfo = this.collInfoServiceImpl.findObjectById(id);
		if (collInfo == null) {
			collInfo = new CollInfo();
			collInfo.setCollname("");
			collInfo.setColldate(Calendar.getInstance().getTime()); // 初始征集日期为当前日期
		}

		ModelAndView mav = new ModelAndView("coll/collinfo/edit");
		mav.addObject("collInfo", collInfo);

		return mav;
	}

	@ResponseBody
	@RequestMapping("/update")
	@DuplicateSubmission(needRemoveToken = true)
	public ModelMap addOrUpdate(CollInfo record) {
		ModelMap modelMap = new ModelMap();
		int rows = this.collInfoServiceImpl.save(record);
		modelMap.addAttribute("successRows", rows);
		modelMap.addAttribute("data", record);
		return modelMap;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public ModelMap delete(
			@RequestParam(value = "ids[]", required = false) String[] ids) {
		ModelMap modelMap = new ModelMap();
		int rows = this.collInfoServiceImpl.delete(ids);
		modelMap.addAttribute("successRows", rows);
		return modelMap;
	}
}