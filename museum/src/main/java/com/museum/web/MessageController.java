package com.museum.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.framework.common.anaotation.DuplicateSubmission;
import com.framework.mybatis.util.PageResult;
import com.framework.web.controller.BaseController;
import com.museum.model.MessageInbox;
import com.museum.service.MessageService;
import com.system.common.SysConstant;
import com.system.model.SysDepartmentTree;
import com.system.model.SysUser;
import com.system.mybatis.service.ISysDeptService;
import com.system.mybatis.service.ISystemUserService;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

	@Autowired
	private MessageService messageServiceImpl;

	@Autowired
	@Qualifier("sysDeptServiceImpl")
	private ISysDeptService sysDeptService;

	@Autowired
	private ISystemUserService systemUserServiceImpl;

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("message/messageindex");

		SysUser user = (SysUser) this.getSessionUser();
		String userId = user.getUserid();

		List<MessageInbox> noReadMsgs = this.messageServiceImpl
				.getUserMessage(userId);

		mav.addObject("messages", noReadMsgs);
		return mav;
	}

	@RequestMapping("/list")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView dataList(String userId, String searchContent,
			String type, Integer pageNo, Integer pageNum) {
		ModelAndView mav = new ModelAndView("message/listdata");
		PageResult page = genPageResult(pageNo, pageNum);
		if (userId == null || "".equals(userId)) {
			SysUser user = (SysUser) this.getSessionUser();
			userId = user.getUserid();
		}

		if (type == null)
			type = "accept";

		this.messageServiceImpl.getMessage(userId, searchContent, page, type);

		if ("send".equals(type) || "draft".equals(type))
			mav.addObject("outbox", true);

		mav.addObject("param", "{\"type\":\"" + type
				+ "\",\"searchContent\":\"" + searchContent + "\"}");
		mav.addObject("page", page);
		return mav;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private PageResult genPageResult(Integer pageNo, Integer pageNum) {
		if (pageNum == null || pageNum == 0) {
			pageNum = SysConstant.SYSDEFAULTROWNUM;
		}
		if (pageNo == null || pageNo == 1) {
			pageNo = 1;
		}

		PageResult page = new PageResult(pageNo, pageNum);
		return page;
	}

	/**
	 * 创建新消息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/showMessage")
	@DuplicateSubmission(needSaveToken = true)
	public ModelAndView showEdit(String id) {
		ModelAndView mav = new ModelAndView("message/edit");
		MessageInbox messageInbox = this.messageServiceImpl.findObjectById(id);

		List<SysDepartmentTree> deptTree = this.sysDeptService
				.getDeptBySupperId("xxx");

		if (messageInbox == null)
			messageInbox = new MessageInbox();

		mav.addObject("message", messageInbox);
		mav.addObject("departmentTree", JSON.toJSON(deptTree).toString());
		return mav;
	}

	/**
	 * 回复消息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/showReply")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DuplicateSubmission(needSaveToken = true)
	public ModelAndView showReply(
			@RequestParam(value = "ids[]", required = false) String[] ids,
			Integer pageNo, Integer pageNum) {

		ModelAndView mav = new ModelAndView("message/replymsg");

		// 取要回复的人员信息，只显示第一个选择的消息
		MessageInbox msgInbox = this.messageServiceImpl.findObjectById(ids[0]);
		SysUser fromUser = this.systemUserServiceImpl.findObjectById(msgInbox
				.getFromUserId());
		PageResult page = genPageResult(pageNo, pageNum);
		SysUser user = (SysUser) this.getSessionUser();

		this.messageServiceImpl.getMsgByFromUserAndToUser(fromUser.getId(),
				user.getUserid(), page, "");

		mav.addObject("page", page);
		mav.addObject("user", fromUser);
		return mav;
	}

	@ResponseBody
	@RequestMapping("/update")
	@DuplicateSubmission(needRemoveToken = true)
	public ModelMap addOrUpdate(String userids, String content, boolean isSend,
			String msgBoxId, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		if (msgBoxId == null)
			msgBoxId = "";
		StringBuffer id = new StringBuffer(msgBoxId); // 这里利用，stringbuffer对象取回新增消息，保存草稿时的id，并返回到页面
		int rows = this.messageServiceImpl.save(userids, content, isSend, id,
				null);
		// int rows = this.messageServiceImpl.save(record);
		modelMap.addAttribute("successRows", rows);
		modelMap.addAttribute("msgboxid", id.toString());
		request.setAttribute("isSuccess", false);
		return modelMap;
	}

	/**
	 * 删除一行或多行数据
	 * 
	 * @param ids
	 * @param curType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public ModelMap delete(
			@RequestParam(value = "ids[]", required = false) String[] ids,
			String curType) {
		ModelMap modelMap = new ModelMap();
		int rows = this.messageServiceImpl.delete(ids, curType);// 针对收件箱，只是逻辑删除
		modelMap.addAttribute("successRows", rows);
		return modelMap;
	}

}