package com.system.mybatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.service.AbstractBusinessService;
import com.system.model.SysLog;
import com.system.mybatis.dao.SysLogMapper;
import com.system.mybatis.service.ISystemLogService;

public class SystemLogServiceImpl extends AbstractBusinessService<SysLog>
		implements ISystemLogService {

	@Autowired
	public SysLogMapper sysLogMapper;

	@Override
	public BaseDao getDao() {
		// TODO Auto-generated method stub
		return this.sysLogMapper;
	}

	/**
	 * 
	 */
	@Override
	public int saveLog(SysLog log) {

		int rows = 0;
		log.setLogid(UUIDUtil.getUUID());
		rows = this.sysLogMapper.insertSelective(log);
		
		return rows;

	}

}
