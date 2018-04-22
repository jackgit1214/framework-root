package com.museum.web;

import com.alibaba.fastjson.JSON;
import com.framework.common.anaotation.DuplicateSubmission;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.framework.web.controller.BaseController;
import com.museum.model.CulShowCustom;
import com.museum.service.CulShowCustomService;
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
@RequestMapping("/culshowcustom")
public class CulShowCustomController extends BaseController {
    @Autowired
    private CulShowCustomService culShowCustomServiceImpl;

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("culshowcustom/listindex");
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
        PageResult<CulShowCustom> page = new PageResult<CulShowCustom>(pageNo,pageNum);
        try {this.culShowCustomServiceImpl.findObjectsByPage(queryModel,page);} catch(Exception e) {e.printStackTrace();}
        ModelAndView mav = new ModelAndView("culshowcustom/listdata");
        mav.addObject("param", JSON.toJSONString(queryModel));
        mav.addObject("page", page);
        return mav;
    }

    @RequestMapping("/showEdit")
    @DuplicateSubmission(needSaveToken=true)
    public ModelAndView showEdit(String id) {
        CulShowCustom culShowCustom = this.culShowCustomServiceImpl.findObjectById(id);
        if (culShowCustom == null)
          culShowCustom = new CulShowCustom();
        ModelAndView mav = new ModelAndView("culshowcustom/edit");
        mav.addObject("culShowCustom",culShowCustom);
        return mav;
    }

    @ResponseBody
    @RequestMapping("/update")
    @DuplicateSubmission(needRemoveToken=true)
    public ModelMap addOrUpdate(CulShowCustom record) {
        ModelMap modelMap = new ModelMap();
        int rows = this.culShowCustomServiceImpl.save(record);
        modelMap.addAttribute("successRows",rows);
        modelMap.addAttribute("data",record);
        return modelMap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ModelMap delete(@RequestParam(value = "ids[]", required = false) String[] ids) {
        ModelMap modelMap = new ModelMap();
        int rows = this.culShowCustomServiceImpl.delete(ids);
        modelMap.addAttribute("successRows",rows);
        return modelMap;
    }
}