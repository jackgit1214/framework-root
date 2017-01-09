package com.system.mybatis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.system.common.SysConstant;
import com.system.model.SysModule;
import com.system.model.SysRole;
import com.system.model.SysRoleUser;
import com.system.model.SysUser;
import com.system.mybatis.dao.SysRoleMapper;
import com.system.mybatis.dao.SysRoleUserMapper;
import com.system.mybatis.dao.SysUserMapper;
import com.system.mybatis.service.ISysModuleService;
import com.system.mybatis.service.ISystemUserService;

@Service
@Transactional(readOnly=true)
public class SystemUserServiceImpl extends AbstractBusinessService<SysUser>
		implements ISystemUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private ISysModuleService sysModuleServiceImpl;

	
	@Autowired
	private SysRoleUserMapper sysRoleUserMapper;
	
	@Override
	public BaseDao getDao() {
		// TODO Auto-generated method stub
		return this.sysUserMapper;
	}

	@Override
	public SysUser checkUserLogin(String userid, String password) {
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String md5Password = md5.encodePassword(userid, password);
		criteria.andEqualTo("logincode", userid);
		criteria.andEqualTo("password", md5Password);

		List<SysUser> users = this.findObjects(queryModel);
		if (users != null && users.size() > 0)
			return users.get(0);

		return null;
	}

	@Override
	public boolean changePWD(String loginid, String password) {
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String md5Password = md5.encodePassword(loginid, password);
		criteria.andEqualTo("userid", loginid);
		SysUser user = new SysUser();
		user.setPassword(md5Password);
		int rows = this.sysUserMapper.updateByConditionSelective(user,
				queryModel);
		if (rows > 0)
			return true;

		return false;
	}

	/**
	 * 保存用户 当用户ID为空时，增加用户数据。
	 */
	@Override
	public int saveUser(SysUser user) {

		int rows = 0;

		if (user.getUserid() == null || "".equals(user.getUserid())) {
			Md5PasswordEncoder md5 = new Md5PasswordEncoder();
			user.setPassword(md5.encodePassword(user.getLogincode(),
					SysConstant.SYSDEFAULTPASSWORD));
			user.setUserid(UUIDUtil.getUUID());
			rows = this.sysUserMapper.insertSelective(user);
		} else
			rows = this.sysUserMapper.updateByPrimaryKeySelective(user);

		return rows;
	}

	/**
	 * 保存用户 当用户ID为空时，增加用户数据。
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public int saveUser(SysUser user,String[] roleids) {

		int rows = 0;
		String userid = null;
		if (user.getUserid() == null || "".equals(user.getUserid())) {
			
			Md5PasswordEncoder md5 = new Md5PasswordEncoder();
			user.setPassword(md5.encodePassword(user.getLogincode(),
					SysConstant.SYSDEFAULTPASSWORD));
			userid = UUIDUtil.getUUID();
			user.setUserid(userid);
			rows = this.sysUserMapper.insertSelective(user);
		} else{
			rows = this.sysUserMapper.updateByPrimaryKeySelective(user);
			userid =  user.getUserid();
		}
		if (roleids!=null)
			this.saveRoleUser(userid, roleids);
		return rows;
	}
	
	/**
	 * 存储角色与人员关联表数据，
	 * 先删除后插入
	 * @param userid
	 * @param roleids
	 */
	private void saveRoleUser(String userid,String[] roleids){
		
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		
		criteria.andEqualTo("userid", userid);
	
		//先删除后增加
		this.sysRoleUserMapper.deleteByCondition(queryModel);
		for (String roleid : roleids) {
			if (roleid==null||"".equals(roleid.trim()))
				continue;
			SysRoleUser sysRoleUser = new SysRoleUser();
			
			sysRoleUser.setRoleid(roleid);
			sysRoleUser.setUserid(userid);
			this.sysRoleUserMapper.insert(sysRoleUser);
		}
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int delete(String[] ids) {

		int rows = 0;
		for (String id : ids) {
			rows = rows + this.sysUserMapper.deleteByPrimaryKey(id);
			QueryModel queryModel = new QueryModel();
			QueryModel.Criteria criteria = queryModel.createCriteria();
			
			criteria.andEqualTo("userid", id);
			this.sysRoleUserMapper.deleteByCondition(queryModel);
		}
		return rows;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int delete(String ids) {
		int rows = this.sysUserMapper.deleteByPrimaryKey(ids);
		return rows;
	}

	@Override
	public List<SysUser> getUsersByDeptid(String deptid) {

		return this.sysUserMapper.selectUserByDeptId(deptid);

	}

	@Override
	public List<SysModule> getUserModule(SysUser user, String parentid) {

		List<SysModule> sysModules = null;

		/* 是系统管理员时 */
		if (SysConstant.SYSDEFAULTMANAGER.equalsIgnoreCase(user.getLogincode())) {
			this.sysModuleServiceImpl.findMenusByParentId(parentid);
		} else {
			this.sysModuleServiceImpl.findMenusByUser(user, parentid);
		}

		return sysModules;
	}

	@Override
	public List<SysRole> getUserRole(SysUser user) {
		// TODO Auto-generated method stub
		QueryModel queryModel = new QueryModel();
		queryModel.setOrderByClause("a.rolename");
		
		List<SysRole> sysRoles = this.sysRoleMapper.getSysRoleByUser(user.getUserid(), queryModel);
		return sysRoles;
	}

}
