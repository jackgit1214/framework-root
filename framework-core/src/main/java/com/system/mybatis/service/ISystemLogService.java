package com.system.mybatis.service;

import com.framework.mybatis.service.IBusinessService;
import com.system.model.SysLog;

public interface ISystemLogService extends IBusinessService<SysLog> {

	public int saveLog(SysLog log) ;
	
	
}
