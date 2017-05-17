package com.museum.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.web.controller.BaseController;
import com.museum.model.CommCode;
import com.museum.service.CommCodeService;
import com.system.model.SysCodeTree;
import com.system.mybatis.service.ISysCodeService;

@Controller
@RequestMapping("/public/widget")
public class CommonController extends BaseController {

	@Autowired
	private ISysCodeService sysCodeService;

	@Autowired
	private CommCodeService commCodeServiceImpl;

	@ResponseBody
	@RequestMapping("/getCodes")
	public ModelMap getCodeByType(String codeType) {
		ModelMap modelMap = new ModelMap();

		List<SysCodeTree> codes = sysCodeService.getCodeDataByCodeid(null,
				codeType);

		modelMap.addAttribute("codes", codes);
		return modelMap;
	}

	@ResponseBody
	@RequestMapping("/getCommCodes")
	public ModelMap getCommCodeByType(String table, String field) {
		ModelMap modelMap = new ModelMap();

		List<CommCode> codes = commCodeServiceImpl.getCommCodes(table, field);

		modelMap.addAttribute("codes", codes);
		return modelMap;
	}

}
