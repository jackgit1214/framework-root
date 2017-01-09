package com.system.web.dataindex;

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
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.framework.web.controller.BaseController;
import com.system.common.SysConstant;
import com.system.model.DataIndexTree;
import com.system.model.SysCodeTree;
import com.system.model.SysIndexitem;
import com.system.mybatis.service.IIndexItemService;
import com.system.mybatis.service.ISysCodeService;

@Controller
@RequestMapping("/system/dataindex")
public class IndexItemController extends BaseController {

	@Autowired
	private IIndexItemService indexItemService;

	@Autowired
	private ISysCodeService  sysCodeService;
	/**
	 * 显示指标列表数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		//int pageNum = SysConstant.SYSDEFAULTROWNUM;
		//PageResult<SysIndexitem> page = new PageResult<SysIndexitem>(1, pageNum);
		
		List<DataIndexTree> dataindexs = this.indexItemService.getIndexTreeByIndexid("GB");
		ModelAndView mav = new ModelAndView("system/dataindex/listindex");
		mav.addObject("dataindex",JSON.toJSON(dataindexs).toString());
		log.debug(JSON.toJSON(dataindexs).toString());
		return mav;
	}
	
	
	/**
	 * 显示指标列表数据
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
		PageResult<SysIndexitem> page = new PageResult<SysIndexitem>(pageNo, pageNum);
		try {
			queryModel.setOrderByClause("indexid");
			this.indexItemService.findObjectsByPage(queryModel, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("system/dataindex/listdata");
		
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
	@RequestMapping("/showEditIndex")
	public ModelAndView showEditIndexWin(String id,DataIndexTree node) {
		ModelAndView mav = new ModelAndView("system/dataindex/editIndex");
		SysIndexitem sysIndexitem = this.indexItemService.findObjectById(id);
		List<SysCodeTree> codes =  sysCodeService.getCodeDataByCodeid("xxx",null);
		if (sysIndexitem == null){
			sysIndexitem = new SysIndexitem();
			sysIndexitem.setIndexid(node.getIndexid());
		}
		//设置上级指标名称及代码
		//增加时首先选择上级代码
		sysIndexitem.setSuperid(node.getIndexid());
		sysIndexitem.setRemark(node.getIndexname());
		mav.addObject("sysIndexitem", sysIndexitem);
		mav.addObject("codes",codes);
		return mav;
	}

	/**
	 * 修改或保存数据
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/updateIndex")
	public ModelMap addOrUpdateIndex(SysIndexitem sysIndexitem) {
		ModelMap mm = new ModelMap();
		int rows = this.indexItemService.saveIndexItem(sysIndexitem);
		mm.addAttribute("successRows", rows);
		mm.addAttribute("indexdata",sysIndexitem);
		return mm;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getIndexdata",method=RequestMethod.POST)
	public List<DataIndexTree> getIndexData(@RequestParam(value = "id", required = false)String id,@RequestParam(value = "pId", required = false)String pId){
		List<DataIndexTree> dataindexs = this.indexItemService.getIndexTreeByIndexid(id);
		return dataindexs;
	}
	
	
	/**
	 * 删除指标数据
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/deleteIndex")
	public ModelMap deleteRole(
			@RequestParam(value = "ids[]", required = false) String[] ids) {
		ModelMap mm = new ModelMap();
		int rows = this.indexItemService.delete(ids);
		mm.addAttribute("successRows", rows);
		return mm;
	}	
}
