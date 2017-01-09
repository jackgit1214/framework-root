package com.system.web.code;

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
import com.framework.common.anaotation.DuplicateSubmission;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.framework.web.controller.BaseController;
import com.system.common.SysConstant;
import com.system.model.SysCode;
import com.system.model.SysCodeTree;
import com.system.mybatis.service.ISysCodeService;

@Controller
@RequestMapping("/system/code")
public class CodeController extends BaseController {

	
	@Autowired
	private ISysCodeService  sysCodeService;
	
	/**
	 * 显示模块首页
	 * @param id
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("system/syscode/listindex");
		
		List<SysCodeTree> codeTrees = this.sysCodeService.getCodeDataByCodeid("xxx",null);
		
		mav.addObject("codeTrees",JSON.toJSON(codeTrees).toString());
		return mav;
	}
	
	/**
	 * 显示代码列表数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView dataList(QueryModel queryModel,Integer pageNo, Integer pageNum) {
		
		if (pageNum==null || pageNum==0){
			pageNum = SysConstant.SYSDEFAULTROWNUM;
		}

		if (pageNo==null || pageNo==1){
			pageNo=1;
		}
		queryModel.reInitCriteria();
		PageResult<SysCode> page = new PageResult<SysCode>(pageNo, pageNum);
		try {
			queryModel.setOrderByClause("code");
			this.sysCodeService.findObjectsByPage(queryModel, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("system/syscode/listdata");
		
		mav.addObject("param",JSON.toJSONString(queryModel));
		mav.addObject("page",page);
		return mav;
	}
	

	/**
	 * 显示新增或编辑界面
	 * 
	 * @param id
	 *            id为空时显示的是新增界面，否则显示的是编辑界面。
	 * @return
	 */
	@DuplicateSubmission(needSaveToken=true)
	@RequestMapping("/showEditCode")
	public ModelAndView showEditWin(String id,SysCodeTree node) {
		ModelAndView mav = new ModelAndView("system/syscode/editcode");
		SysCode sysCode = this.sysCodeService.findObjectById(id);
		if (sysCode == null){
			sysCode = new SysCode();
			sysCode.setApplyto(node.getApplyto());
			sysCode.setCodetype(node.getCodetype());
			sysCode.setContent(node.getContent());
			sysCode.setSuperid(node.getCode());
			sysCode.setLeve(Short.valueOf(String.valueOf(node.getLeve()+1)));
		}
		mav.addObject("sysCode", sysCode);
		return mav;
	}

	/**
	 * 修改或保存数据
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/updateCode")
	@DuplicateSubmission(needRemoveToken=true)
	public ModelMap addOrUpdateCode(SysCode sysCode) {
		ModelMap mm = new ModelMap();
		int rows = this.sysCodeService.saveCode(sysCode);
		mm.addAttribute("successRows", rows);
		mm.addAttribute("codedata",sysCode);
		return mm;
	}
	
	
	/**
	 * 删除代码数据
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/delete")
	public ModelMap deleteRole(
			@RequestParam(value = "ids[]", required = false) String[] ids) {
		ModelMap mm = new ModelMap();
		int rows = this.sysCodeService.delete(ids);
		mm.addAttribute("successRows", rows);
		return mm;
	}	
	
	
	@ResponseBody
	@RequestMapping(value="/getCodedata",method=RequestMethod.POST)
	public List<SysCodeTree> getCodeData(@RequestParam(value = "id", required = false)String id,@RequestParam(value = "codetype", required = false)String codetype){
		List<SysCodeTree> datacodes = this.sysCodeService.getCodeDataByCodeid(id,codetype);
		return datacodes;
	}	
	
	
}
