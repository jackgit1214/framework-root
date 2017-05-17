package com.museum.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.framework.web.controller.BaseController;
import com.museum.model.CollClue;
import com.museum.service.ICollIntegrationService;

@Controller
@RequestMapping("/coll")
public class CollMainController extends BaseController {

	@Autowired
	private ICollIntegrationService collIntegrationServiceImpl;

	@RequestMapping
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("coll/index");

		List<CollClue> collClues = this.collIntegrationServiceImpl
				.getCollClueInfoByStatus("0");
		mav.addObject("collClues", collClues);

		return mav;
	}

	@ResponseBody
	@RequestMapping("/statistics")
	public ModelMap getCollNum(String curdate, int type) {
		ModelMap modelMap = new ModelMap();

		List<String> xAxis = new ArrayList<String>();
		List<Map<String, Integer>> statData = this.collIntegrationServiceImpl
				.getCollNumStatistic(curdate, type, xAxis);
		modelMap.addAttribute("statisticData", statData);
		modelMap.addAttribute("xAxisData", xAxis);
		return modelMap;
	}

	@ResponseBody
	@RequestMapping("/culStatistics")
	public ModelMap cultrueStat(String curdate) {

		ModelMap modelMap = new ModelMap();
		List<String> legend = new ArrayList<String>();
		List<Map<String, Integer>> statData = this.collIntegrationServiceImpl
				.culTypeStatistic(curdate, legend);

		modelMap.addAttribute("legend", legend);
		modelMap.addAttribute("statisticData", statData);

		return modelMap;
	}

}