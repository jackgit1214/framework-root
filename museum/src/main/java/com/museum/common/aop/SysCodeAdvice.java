package com.museum.common.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.museum.model.CommCode;
import com.museum.service.CommCodeService;
import com.system.model.SysCode;
import com.system.mybatis.service.ISysCodeService;

@Aspect
@Component
public class SysCodeAdvice {

	@Autowired
	private ISysCodeService sysCodeService;

	@Autowired
	private CommCodeService commCodeServiceImpl;

	@Pointcut("@annotation(com.museum.common.aop.NeedCode)")
	public void showPage() {
	}

	@AfterReturning(value = "showPage()", returning = "retValue")
	public void actionAfter(JoinPoint joinPoint, Object retValue)
			throws Throwable {

		MethodSignature methodSignature = (MethodSignature) joinPoint
				.getSignature();
		Method targetMethod = methodSignature.getMethod();
		NeedCode needCode = targetMethod.getAnnotation(NeedCode.class);

		String[] codeTypes = needCode.codeType();
		String[] commonTypes = needCode.commCodeType();
		Map<String, List<SysCode>> mapCodeTypes = this.getCodeType(codeTypes);
		Map<String, List<CommCode>> mapCommTypes = this
				.getCommonCodeType(commonTypes);
		if (retValue instanceof ModelAndView) {
			ModelAndView mav = (ModelAndView) retValue;

			for (String codeType : codeTypes) {
				if (mapCodeTypes.containsKey(codeType))
					mav.addObject(codeType, mapCodeTypes.get(codeType));
			}
			for (String codeType : commonTypes) {
				if (mapCommTypes.containsKey(codeType))
					mav.addObject(codeType, mapCommTypes.get(codeType));
			}

		} else {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			for (String codeType : codeTypes) {
				if (mapCodeTypes.containsKey(codeType))
					request.setAttribute(codeType, mapCodeTypes.get(codeType));
			}
			for (String codeType : commonTypes) {
				if (mapCommTypes.containsKey(codeType))
					request.setAttribute(codeType, mapCommTypes.get(codeType));
			}

		}

		// return joinPoint.proceed();
	}

	private Map<String, List<SysCode>> getCodeType(String[] codeTypes) {

		Map<String, List<SysCode>> mapCodeType = new HashMap<String, List<SysCode>>();
		// 2代码是指标型管理数据，需要动态添加

		List<SysCode> datas = this.sysCodeService.getCodeData(codeTypes);
		if (datas.isEmpty())
			return null;
		for (SysCode code : datas) {
			List<SysCode> tmpCode = null;
			String codeType = code.getCodetype();
			if (mapCodeType.containsKey(codeType)) {
				tmpCode = mapCodeType.get(codeType);
				tmpCode.add(code);
			} else {
				tmpCode = new ArrayList<SysCode>();
				tmpCode.add(code);
				mapCodeType.put(codeType, tmpCode);
			}
		}
		// TODO Auto-generated method stub
		return mapCodeType;

	}

	private Map<String, List<CommCode>> getCommonCodeType(String[] commonTypes) {

		Map<String, List<CommCode>> mapCodeType = new HashMap<String, List<CommCode>>();
		// 2代码是指标型管理数据，需要动态添加
		List<CommCode> datas = this.commCodeServiceImpl
				.getCommCodes(commonTypes);

		if (datas.isEmpty())
			return null;
		for (CommCode code : datas) {
			List<CommCode> tmpCode = null;
			String codeType = code.getTablename();
			if (mapCodeType.containsKey(codeType)) {
				tmpCode = mapCodeType.get(codeType);
				tmpCode.add(code);
			} else {
				tmpCode = new ArrayList<CommCode>();
				tmpCode.add(code);
				mapCodeType.put(codeType, tmpCode);
			}
		}
		// TODO Auto-generated method stub
		return mapCodeType;

	}
}
