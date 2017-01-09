package com.system.mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.framework.mybatis.service.IBusinessService;
import com.system.model.SysModule;
import com.system.model.SysRole;
import com.system.model.SysUser;

@Service
public interface ISysModuleService extends IBusinessService<SysModule> {
	
	List<SysModule> findMenusByUser(SysUser user,String parentid);
	
	List<SysModule> findMenusByParentId(String parentId);

	List<SysModule> findMenusByRole(SysRole role);

}
