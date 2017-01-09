/**
 * 
 */
package com.framework.web.advice;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSONObject;
import com.framework.common.converter.CustomDoubleEditor;
import com.framework.common.converter.CustomFloatEditor;
import com.framework.common.converter.CustomIntegerEditor;
import com.framework.common.converter.CustomLongEditor;
import com.framework.common.converter.CustomShortEditor;
import com.framework.common.converter.SpecCustomBooleanEditor;
import com.framework.exception.BusinessException;
@ControllerAdvice
public class ControlAdvice {

	protected final Log log = LogFactory.getLog(this.getClass());
	/***
	 *  此方法返回类型与方法有参数有固定的类型 ，其它类型时会不起作用
	 *  具体参见@ExceptionHandler的帮助  
	 * @param be
	 * @param request
	 * @param nw
	 * @return
	 */
	//@ResponseBody //页面端ajax调用时这里会起作用
	/**
	 * 处理邦定异常 
	 */
//	@ExceptionHandler(BindException.class)
//	public Object  exceptionHandler(Exception ex,WebRequest request,NativeWebRequest nw,HttpServletRequest rq,HttpServletResponse response) {
//
//		Map<String,String> hm = new HashMap<String,String>();
//		BindingResult bindingResult = ((BindException) ex).getBindingResult();
//		List<ObjectError> errors =  bindingResult.getAllErrors();
//		for (ObjectError error:errors){
//			hm.put(error.getCode(), error.getDefaultMessage());
//		}
//	
//		//其它方式调用action时
//		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
//				.getHeader("X-Requested-With") != null && request
//				.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
//			return "responseMessage";
//		}else{
//			//当为AJAX调用，JSON格式接收数据时
//			try {
//				PrintWriter writer = response.getWriter();
//				writer.write(ex.getMessage());
//				writer.flush();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return hm;
//		}
//	}	

	/**
	 * ajax调用的绑定异常
	 * @param ex
	 * @param request
	 * @param nw
	 * @param rq
	 * @param response
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public Object  exceptionHandler(Exception ex,WebRequest request,NativeWebRequest nw,HttpServletRequest rq,HttpServletResponse response) {

		Map<String,String> hm = new HashMap<String,String>();
		BindingResult bindingResult = ((BindException) ex).getBindingResult();
		List<ObjectError> errors =  bindingResult.getAllErrors();
		String message="";
		for (ObjectError error:errors){
			if (error instanceof FieldError){
				FieldError fieldError = (FieldError)error;
				message = message+"["+fieldError.getField()+"]["+fieldError.getRejectedValue()+"]";
			}
			message = message+error.getDefaultMessage()+"\r\n";
		}
		hm.put("error",message);
		return hm;
		
	}	
	
	
	/**
	 * 这里处理业务抛出异常
	 * @param be
	 * @param request
	 * @param nw
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	public void business_exceptionHandler(Exception be,HttpServletRequest request,HttpServletResponse response) throws IOException {

		Map<String,Object> hm = new HashMap<String,Object>();	
		
		if (be instanceof BusinessException){
			
			hm.put("classname",be.getClass());
			hm.put("message",be.getMessage());
			hm.put("cause", be.toString());
			hm.put("success",false);
			
		}
		//WebUtils.showErrorMessage(response);
		//WebUtils.alertAndBack("test", response);
		//其它方式调用action时
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
				.getHeader("X-Requested-With") != null && request
				.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				//WebUtils.showErrorMessage(response);
				
		}else{
			//当为AJAX调用，JSON格式接收数据时
			try {
				PrintWriter writer = response.getWriter();
				JSONObject jsonObject = new JSONObject(hm);
				//response.setContentType("application/json;charset=utf-8");
				//response.setHeader("Cache-Control", "no-cache");		
				String outString = jsonObject.toJSONString();
				writer.write(outString);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//return null;
		}
			
		//return hm;
	}	
	
	
	private Object handle(){
		Map<String,String> hm = new HashMap<String,String>();
		
		return hm;
	}
	
	
	

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		//PropertyEditorRegistrySupport
		// 注册int,float,long,double类型转换
		binder.registerCustomEditor(int.class,new CustomIntegerEditor() );
		binder.registerCustomEditor(float.class,new CustomFloatEditor() );
		binder.registerCustomEditor(double.class,new CustomDoubleEditor() );
		binder.registerCustomEditor(long.class,new CustomLongEditor() );
		binder.registerCustomEditor(short.class,new CustomShortEditor() );
		
		//注册boolean类型转换，缺省值为false
		binder.registerCustomEditor(boolean.class,new SpecCustomBooleanEditor(false) );
		
		// 注册日期转换
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

	}

	@ModelAttribute
	public void modelAttribute() {
		
	}
	
	class AjaxResInfo{
		private String fieldname;
		private Map<String,String> errorInfo;
		
		public void putInfo(String fieldName,String msg){
			if (this.errorInfo.containsKey(fieldName)){
				
			}
		}
		
		public String getFieldname() {
			return fieldname;
		}
		public void setFieldname(String fieldname) {
			this.fieldname = fieldname;
		}
		
		public Map<String, String> getErrorInfo() {
			return errorInfo;
		}
		public void setErrorInfo(Map<String, String> errorInfo) {
			this.errorInfo = errorInfo;
		}
		
		
		
	}
}
