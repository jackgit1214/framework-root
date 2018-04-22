package com.museum.web;

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
import com.museum.model.CulIndexDetailDataWithBLOBs;
import com.museum.model.CulMainCul;
import com.museum.service.CulMainCulService;
import com.system.common.SysConstant;
import com.system.model.DataIndexTree;
import com.system.mybatis.service.IIndexItemService;

@Controller
@RequestMapping("/culmaincul")
public class CulMainCulController extends BaseController {

    @Autowired
    private CulMainCulService culMainCulServiceImpl;

    @Autowired
    private IIndexItemService indexItemService;

    @RequestMapping("/index")
    public ModelAndView index() {
	ModelAndView mav = new ModelAndView("culmaincul/listindex");
	return mav;
    }

    @RequestMapping("/list")
    public ModelAndView dataList(QueryModel queryModel, Integer pageNo, Integer pageNum) {
	if (pageNum == null || pageNum == 0) {
	    pageNum = SysConstant.SYSDEFAULTROWNUM;
	}
	if (pageNo == null || pageNo == 1) {
	    pageNo = 1;
	}
	queryModel.reInitCriteria();
	PageResult<CulMainCul> page = new PageResult<CulMainCul>(pageNo, pageNum);
	try {
	    this.culMainCulServiceImpl.findObjectsByPage(queryModel, page);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	ModelAndView mav = new ModelAndView("culmaincul/listdata");
	mav.addObject("param", JSON.toJSONString(queryModel));
	mav.addObject("showcols", this.culMainCulServiceImpl.getCustomColumn());
	mav.addObject("page", page);
	return mav;
    }

    @RequestMapping("/showEdit")
    @DuplicateSubmission(needSaveToken = true)
    public ModelAndView showEdit(String id) {
	CulMainCul culMainCul = this.culMainCulServiceImpl.findObjectById(id);
	if (culMainCul == null)
	    culMainCul = new CulMainCul();
	ModelAndView mav = new ModelAndView("culmaincul/edit");
	List<DataIndexTree> dataindexs = this.indexItemService.getIndexTreeByIndexid("GB");
	mav.addObject("dataindex", JSON.toJSON(dataindexs).toString());
	mav.addObject("culIndexData", this.culMainCulServiceImpl.getIndexData(culMainCul.getCulid()));
	mav.addObject("culMainCul", culMainCul);

	return mav;
    }

    @ResponseBody
    @RequestMapping("/update")
    @DuplicateSubmission(needRemoveToken = true)
    public ModelMap addOrUpdate(CulMainCul record,
	    @FormList("culData") List<CulIndexDetailDataWithBLOBs> culIndexDatas) {
	ModelMap modelMap = new ModelMap();
	int rows = this.culMainCulServiceImpl.save(record, culIndexDatas);
	modelMap.addAttribute("successRows", rows);
	modelMap.addAttribute("data", record);
	return modelMap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ModelMap delete(@RequestParam(value = "ids[]", required = false) String[] ids) {
	ModelMap modelMap = new ModelMap();
	int rows = this.culMainCulServiceImpl.delete(ids);
	modelMap.addAttribute("successRows", rows);
	return modelMap;
    }
}