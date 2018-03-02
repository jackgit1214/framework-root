package com.museum.web;

import com.alibaba.fastjson.JSON;
import com.framework.common.anaotation.DuplicateSubmission;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.framework.web.controller.BaseController;
import com.museum.model.Restorationinfo;
import com.museum.service.RestorationinfoService;
import com.system.common.SysConstant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/restorationinfo")
public class RestorationinfoController extends BaseController {
    @Autowired
    private RestorationinfoService restorationinfoServiceImpl;

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("restorationinfo/listindex");
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
        PageResult<Restorationinfo> page = new PageResult<Restorationinfo>(pageNo,pageNum);
        try {this.restorationinfoServiceImpl.findObjectsByPage(queryModel,page);} catch(Exception e) {e.printStackTrace();}
        ModelAndView mav = new ModelAndView("restorationinfo/listdata");
        mav.addObject("param", JSON.toJSONString(queryModel));
        mav.addObject("page", page);
        return mav;
    }

    @RequestMapping("/showEdit")
    @DuplicateSubmission(needSaveToken=true)
    public ModelAndView showEdit(String id) {
        Restorationinfo restorationinfo = this.restorationinfoServiceImpl.findObjectById(id);
        if (restorationinfo == null)
          restorationinfo = new Restorationinfo();
        ModelAndView mav = new ModelAndView("restorationinfo/edit");
        mav.addObject("restorationinfo",restorationinfo);
        return mav;
    }

    @ResponseBody
    @RequestMapping("/update")
    @DuplicateSubmission(needRemoveToken=true)
    public ModelMap addOrUpdate(Restorationinfo record) {
        ModelMap modelMap = new ModelMap();
        int rows = this.restorationinfoServiceImpl.save(record);
        modelMap.addAttribute("successRows",rows);
        modelMap.addAttribute("data",record);
        return modelMap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ModelMap delete(@RequestParam(value = "ids[]", required = false) String[] ids) {
        ModelMap modelMap = new ModelMap();
        int rows = this.restorationinfoServiceImpl.delete(ids);
        modelMap.addAttribute("successRows",rows);
        return modelMap;
    }
}