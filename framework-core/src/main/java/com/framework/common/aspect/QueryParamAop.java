package com.framework.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.framework.mybatis.model.QueryModel;
import com.system.common.SysConstant;

@Aspect
@Component
public class QueryParamAop {

	@Pointcut("@annotation(com.framework.common.anaotation.QueryModelParam) && args(queryModel,pageNo,pageNum)")
	private void queryModelHandle(QueryModel queryModel, Integer pageNo,
			Integer pageNum) {

	}

	@Around(value = "queryModelHandle(queryModel,pageNo,pageNum)", argNames = "queryModel,pageNo,pageNum")
	public Object actionAopAround(ProceedingJoinPoint joinPoint,
			QueryModel queryModel, Integer pageNo, Integer pageNum)
			throws Throwable {

		if (pageNum == null || pageNum == 0) {
			pageNum = SysConstant.SYSDEFAULTROWNUM;
		}
		if (pageNo == null || pageNo == 1) {
			pageNo = 1;
		}
		Object[] arguments = joinPoint.getArgs();

		for (Object o : arguments) {
			if (o instanceof QueryModel) {
				QueryModel qm = (QueryModel) o;
				qm.reInitCriteria(); // 初始化前端条件
			} else if (o instanceof Integer) {

			}

		}
		return joinPoint.proceed();
	}

	@Before(value = "queryModelHandle(queryModel,pageNo,pageNum)", argNames = "queryModel,pageNo,pageNum")
	public void beforeAction(JoinPoint joinPoint, QueryModel queryModel,
			Integer pageNo, Integer pageNum) {
		if (pageNum == null || pageNum == 0) {
			pageNum = SysConstant.SYSDEFAULTROWNUM;
		}
		if (pageNo == null || pageNo == 1) {
			pageNo = 1;
		}
	}
}
