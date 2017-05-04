/*
 * 取得资源信息
 * 
 * 1、根据附件ID以及需要的图像级别取得单个缩略图
 * 2、根据附件ID取得原图
 * 3、根据附件ID，取得所有缩略图
 * 4、取缺省缩略图，缺省级别是1，没有1取0
 * 5、
 */
package com.resources.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.framework.image.IImageConstant;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.resources.ResConstant;
import com.resources.model.CommAttachments;
import com.resources.services.AttachmentsService;

@Controller
@RequestMapping("/public")
public class ShareResources {

	@Autowired
	private AttachmentsService attachmentsServiceImpl;

	/**
	 * 
	 * @param attaid
	 *            附件ID
	 * @param permission
	 *            影像资源的级别，
	 * @param response
	 */
	@RequestMapping(value = "image/{attaid}", method = RequestMethod.GET)
	public void getImageResource(@PathVariable String attaid,
			@RequestParam(required = false) String permission,
			HttpServletResponse response) {

		response.addHeader("Access-Control-Allow-Origin", "*"); // 允许 跨域访问

		if (permission != null
				&& !IImageConstant.IMAGE_MAXPERMISSION.equals(permission)) {
			byte[] fileContent = this.attachmentsServiceImpl.getResource(
					attaid, permission);

			ServletOutputStream output;
			try {
				output = response.getOutputStream();
				output.write(fileContent, 0, fileContent.length);
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			String filePath = this.attachmentsServiceImpl.getResource(attaid);
			this.getFileStream(filePath, response);
		}
	}

	@RequestMapping(value = "dataimage/{dataId}", method = RequestMethod.GET)
	public void getImageDefaultRes(@PathVariable String dataId,
			@RequestParam(required = false) String permission,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*"); // 允许 跨域访问

		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria().andEqualTo("dataid", dataId)
				.andEqualTo("isDefault", "1");
		queryModel.setOrderByClause("attaNo");

		List<CommAttachments> attaDatas = this.attachmentsServiceImpl
				.findObjects(queryModel);
		if (attaDatas.size() > 0) {
			if (permission != null
					&& !IImageConstant.IMAGE_MAXPERMISSION.equals(permission)) {
				byte[] fileContent = this.attachmentsServiceImpl.getResource(
						attaDatas.get(0).getAttaid(), permission);
				ServletOutputStream output;
				try {
					output = response.getOutputStream();
					output.write(fileContent, 0, fileContent.length);
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 根据数据ID取出所有的附件
	 * 
	 * @param dataId
	 *            数据ID
	 * @param response
	 *            跨域访问时使用
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAttaData/{dataId}")
	public ModelMap getDataList(@PathVariable String dataId,
			HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		response.addHeader("Access-Control-Allow-Origin", "*"); // 允许 跨域访问

		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria().andEqualTo("dataid", dataId);
		queryModel.setOrderByClause("attaNo");

		List<CommAttachments> attaDatas = this.attachmentsServiceImpl
				.findObjects(queryModel);

		modelMap.addAttribute("attachments", attaDatas);
		return modelMap;
	}

	@ResponseBody
	@RequestMapping("/getAttaByType/{type}")
	public ModelMap getDataList(@PathVariable String type, Integer pageNo,
			Integer pageNum, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*"); // 允许 跨域访问
		if (pageNum == null || pageNum == 0) {
			pageNum = ResConstant.SYSDEFAULTROWNUM;
		}
		if (pageNo == null || pageNo == 1) {
			pageNo = 1;
		}
		ModelMap modelMap = new ModelMap();

		PageResult<CommAttachments> page = new PageResult<CommAttachments>(
				pageNo, pageNum);

		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria().andEqualTo("busstype", type);
		queryModel.setOrderByClause("attaNo");

		try {
			this.attachmentsServiceImpl.findObjectsByPage(queryModel, page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		modelMap.addAttribute("attachments", page.getPageDatas());
		return modelMap;
	}

	@RequestMapping("/video")
	public ModelAndView videoResources(String dataid, String busstype) {
		ModelAndView mav = new ModelAndView("commattachments/listindex");
		return mav;
	}

	@RequestMapping(value = "other/{attaid}", method = RequestMethod.GET)
	public void getDocumentResources(@PathVariable String attaid,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*"); // 允许 跨域访问

		String filePath = this.attachmentsServiceImpl.getResource(attaid);
		this.getFileStream(filePath, response);

	}

	private void getFileStream(String filePath, HttpServletResponse response) {

		// response.setContentType("video/avi");
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
