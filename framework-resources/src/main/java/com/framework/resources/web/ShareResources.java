package com.framework.resources.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.framework.resources.services.AttachmentsService;

@Controller
@RequestMapping("/public")
public class ShareResources {

	@Autowired
	private AttachmentsService attachmentsServiceImpl;

	@RequestMapping(value = "/{attaid}", method = RequestMethod.GET)
	public void getResource(@PathVariable String attaid, String permission,
			HttpServletResponse response) {

		response.addHeader("Access-Control-Allow-Origin", "*"); // 允许 跨域访问
		String filePath = this.attachmentsServiceImpl.getResource(attaid,
				permission);
		this.getFileStream(filePath, response);

	}

	@RequestMapping("/image")
	public ModelAndView imageResources(String dataid, String busstype) {
		ModelAndView mav = new ModelAndView("commattachments/listindex");
		return mav;
	}

	@RequestMapping("/video")
	public ModelAndView videoResources(String dataid, String busstype) {
		ModelAndView mav = new ModelAndView("commattachments/listindex");
		return mav;
	}

	@RequestMapping("/doc")
	public ModelAndView docResources(String dataid, String busstype) {
		ModelAndView mav = new ModelAndView("commattachments/listindex");
		return mav;
	}

	@RequestMapping("/pdf")
	public ModelAndView pdfResources(String dataid, String busstype) {
		ModelAndView mav = new ModelAndView("commattachments/listindex");
		return mav;
	}

	@RequestMapping("/other")
	public ModelAndView otherResources(String dataid, String busstype) {
		ModelAndView mav = new ModelAndView("commattachments/listindex");
		return mav;
	}

	private void getFileStream(String filePath, HttpServletResponse response) {
		ServletOutputStream output = null;
		InputStream in = null;
		try {
			File file = new File(filePath);
			in = new FileInputStream(file);
			byte[] tempbytes = new byte[1024];
			int byteread = 0;

			output = response.getOutputStream();

			while ((byteread = in.read(tempbytes)) != -1) {
				output.write(tempbytes, 0, byteread);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				output.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
