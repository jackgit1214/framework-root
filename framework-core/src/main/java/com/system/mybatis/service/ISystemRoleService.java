package com.system.mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.framework.mybatis.service.IBusinessService;
import com.system.model.SysModule;
import com.system.model.SysRole;

@Service
public interface ISystemRoleService extends IBusinessService<SysRole> {

	public List<SysModule> findRoleModuleByRoleid(SysRole role);
	
	public int saveRole(SysRole sysRole);
	
	public int saveRolePermission(SysRole sysRole,String[] modids);
	
	public int delete(String[] ids);

	public int delete(String id);
	
	public Object findRoleModule(SysRole role);
}
