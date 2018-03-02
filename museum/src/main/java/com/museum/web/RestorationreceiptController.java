package com.museum.web;

import com.alibaba.fastjson.JSON;
import com.framework.common.anaotation.DuplicateSubmission;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.framework.web.controller.BaseController;
import com.museum.model.Restorationreceipt;
import com.museum.service.RestorationreceiptService;
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
@RequestMapping("/restorationreceipt")
public class RestorationreceiptController extends BaseController {
    @Autowired
    private RestorationreceiptService restorationreceiptServiceImpl;

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("restorationreceipt/listindex");
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
        PageResult<Restorationreceipt> page = new PageResult<Restorationreceipt>(pageNo,pageNum);
        try {this.restorationreceiptServiceImpl.findObjectsByPage(queryModel,page);} catch(Exception e) {e.printStackTrace();}
        ModelAndView mav = new ModelAndView("restorationreceipt/listdata");
        mav.addObject("param", JSON.toJSONString(queryModel));
        mav.addObject("page", page);
        return mav;
    }

    @RequestMapping("/showEdit")
    @DuplicateSubmission(needSaveToken=true)
    public ModelAndView showEdit(String id) {
        Restorationreceipt restorationreceipt = this.restorationreceiptServiceImpl.findObjectById(id);
        if (restorationreceipt == null)
          restorationreceipt = new Restorationreceipt();
        ModelAndView mav = new ModelAndView("restorationreceipt/edit");
        mav.addObject("restorationreceipt",restorationreceipt);
        return mav;
    }

    @ResponseBody
    @RequestMapping("/update")
    @DuplicateSubmission(needRemoveToken=true)
    public ModelMap addOrUpdate(Restorationreceipt record) {
        ModelMap modelMap = new ModelMap();
        int rows = this.restorationreceiptServiceImpl.save(record);
        modelMap.addAttribute("successRows",rows);
        modelMap.addAttribute("data",record);
        return modelMap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ModelMap delete(@RequestParam(value = "ids[]", required = false) String[] ids) {
        ModelMap modelMap = new ModelMap();
        int rows = this.restorationreceiptServiceImpl.delete(ids);
        modelMap.addAttribute("successRows",rows);
        return modelMap;
    }
}