package com.framework.resources.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.framework.resources.services.AttachmentsService;

@Controller
@RequestMapping("/public/resources")
public class ShareResources {

	@Autowired
	private AttachmentsService attachmentsServiceImpl;

	@RequestMapping("/image")
	public ModelAndView imageResources() {
		ModelAndView mav = new ModelAndView("commattachments/listindex");
		return mav;
	}

	@RequestMapping("/video")
	public ModelAndView videoResources() {
		ModelAndView mav = new ModelAndView("commattachments/listindex");
		return mav;
	}

	@RequestMapping("/doc")
	public ModelAndView docResources() {
		ModelAndView mav = new ModelAndView("commattachments/listindex");
		return mav;
	}

	@RequestMapping("/pdf")
	public ModelAndView pdfResources() {
		ModelAndView mav = new ModelAndView("commattachments/listindex");
		return mav;
	}

	@RequestMapping("/other")
	public ModelAndView otherResources() {
		ModelAndView mav = new ModelAndView("commattachments/listindex");
		return mav;
	}
}
