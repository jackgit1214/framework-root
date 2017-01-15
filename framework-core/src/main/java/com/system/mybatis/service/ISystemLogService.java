package com.system.mybatis.service;

import org.springframework.stereotype.Service;

import com.framework.mybatis.service.IBusinessService;
import com.system.model.SysLog;

@Service
public interface ISystemLogService extends IBusinessService<SysLog> {

	public int saveLog(SysLog log) ;
	
	
}
