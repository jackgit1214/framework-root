package com.framework.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.framework.mybatis.model.QueryModel;

@Aspect
@Component
public class QueryParamAop {

	// Service层切点
	@Pointcut("@annotation(com.framework.common.QueryModelParam)")
	public void queryModelHandle() {
	}

	@Around("queryModelHandle()")
	public Object actionAopAround(ProceedingJoinPoint joinPoint)
			throws Throwable {
		Object[] arguments = joinPoint.getArgs();
		for (Object o : arguments) {
			if (o instanceof QueryModel) {
				QueryModel qm = (QueryModel) o;
				qm.reInitCriteria(); // 初始化前端条件

			}
		}
		return joinPoint.proceed();
	}

}
