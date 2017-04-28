package com.museum.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.framework.web.controller.BaseController;
import com.museum.service.CollClueService;

@Controller
@RequestMapping("/coll")
public class CollMainController extends BaseController {
	@Autowired
	private CollClueService collClueServiceImpl;

	@RequestMapping
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("coll/index");
		return mav;
	}

}