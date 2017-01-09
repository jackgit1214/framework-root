package com.system.mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.framework.mybatis.service.IBusinessService;
import com.system.model.SysModule;
import com.system.model.SysRole;
import com.system.model.SysUser;

@Service
public interface ISystemUserService extends IBusinessService<SysUser> {

	public SysUser checkUserLogin(String userid,String password);
	
	public boolean changePWD(String loginid,String password);
	
	public int saveUser(SysUser users) ;
	
	public int delete(String[] ids);

	public int delete(String id);
	
	public List<SysUser> getUsersByDeptid(String deptid);
	
	public List<SysModule> getUserModule(SysUser user,String parentid);
	
	public List<SysRole> getUserRole(SysUser user);

	public int saveUser(SysUser user, String[] roleids);
	
}
