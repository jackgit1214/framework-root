package com.system.web.custom;

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
import com.framework.web.controller.BaseController;
import com.system.model.SysCode;
import com.system.model.SysCodeTree;
import com.system.mybatis.service.ISysCodeService;

@Controller
@RequestMapping("/system/custom")
public class CustomController extends BaseController {

	
	@Autowired
	private ISysCodeService  sysCodeService;
	
	/**
	 * 显示模块首页
	 * @param id
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		
		ModelAndView mav = new ModelAndView("system/custom/index");
		
		//默认取藏品类别数据
		List<SysCodeTree> codeTrees = this.sysCodeService.getCodeDataByCodeid("xxx", "wwlb");
		
		mav.addObject("codeTrees",JSON.toJSON(codeTrees).toString());
		
		return mav;
		
	}

	/**
	 * 修改或保存数据
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/update")
	public ModelMap addOrUpdateCode(SysCode sysCode) {
		ModelMap mm = new ModelMap();
		int rows = 0;//this.sysCodeService.saveCode(sysCode);
		mm.addAttribute("successRows", rows);
		mm.addAttribute("codedata",sysCode);
		return mm;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getExpandTreeDatas",method=RequestMethod.POST)
	public List<SysCodeTree> getCodeData(@RequestParam(value = "id", required = false)String id,@RequestParam(value = "codetype", required = false)String codetype){
		
		List<SysCodeTree> datacodes = this.sysCodeService.getCodeDataByCodeid(id,codetype);
		
		return datacodes;
		
	}	
		
}
