package com.framework.mybatis.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.framework.common.anaotation.SystemLog;
import com.framework.common.util.UUIDUtil;
import com.framework.web.util.SessionManager;
import com.system.model.SysLog;
import com.system.model.SysUser;
import com.system.mybatis.service.ISystemLogService;

@Aspect
@Component
public class SystemLogAOP {

	@Resource
	protected SessionManager sessionManager;

	@Resource
	protected ISystemLogService systemLogServiceImpl;

	private final Logger logger = LoggerFactory.getLogger(SystemLogAOP.class);

	// Service层切点
	@Pointcut("@annotation(com.framework.common.anaotation.SystemLog)")
	public void serviceAspect() {
	}

	/**
	 * 后置通知，当完成操作后，记录操作日志
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@AfterReturning("serviceAspect()")
	public void doBefore(JoinPoint joinPoint) {
		SysLog log = this.genSystemLog(joinPoint);
		logger.debug("-------------------日志记录成功--------------------------");
		this.systemLogServiceImpl.saveLog(log);

	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		SysLog log = this.genSystemLog(joinPoint);
		log.setExcecode(e.getClass().getName());
		log.setExcedescription(e.getMessage());

		logger.debug("-------------------日志异常记录成功--------------------------");
		this.systemLogServiceImpl.saveLog(log);

	}

	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String getMethodAnnoValue(JoinPoint joinPoint,
			SystemLog.LogAnnoDefi value) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String annoValue = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					switch (value) {
					case DESC:
						annoValue = method.getAnnotation(SystemLog.class)
								.description();
						break;
					case MODULE:
						annoValue = method.getAnnotation(SystemLog.class)
								.moduleId();
						break;
					case OPER:
						annoValue = method.getAnnotation(SystemLog.class)
								.operId();
						break;
					}

				}
			}
		}
		return annoValue;
	}

	private SysLog genSystemLog(JoinPoint joinPoint) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		SysUser sysUser = getSystemUser();
		String params = "";
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";
			}
		}
		SysLog log = null;
		try {
			log = new SysLog();
			log.setLogid(UUIDUtil.getUUID());
			log.setLogdescription(getMethodAnnoValue(joinPoint,
					SystemLog.LogAnnoDefi.DESC));
			log.setLogtime(new Date());
			log.setRequestmethod(joinPoint.getTarget().getClass().getName()
					+ "." + joinPoint.getSignature().getName());
			log.setLogtype(0);
			log.setRequestip(request.getRemoteAddr());
			log.setUserid(sysUser.getUserid());
			log.setReqparam(params);
			log.setModelname("");
			log.setModelopertype("");
			log.setModelname(getMethodAnnoValue(joinPoint,
					SystemLog.LogAnnoDefi.MODULE));
			log.setModelopertype(getMethodAnnoValue(joinPoint,
					SystemLog.LogAnnoDefi.OPER));
		} catch (Exception ex) {
			// 记录本地异常日志
			logger.error("==异常通知异常==");
			logger.error("异常信息:{}", ex.getMessage());
		}

		return log;
	}

	private SysUser getSystemUser() {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		this.sessionManager = (SessionManager) request.getSession()
				.getAttribute("sessionManager");

		if (this.sessionManager == null)
			return null;

		return (SysUser) this.sessionManager.getUser();

	}
}
