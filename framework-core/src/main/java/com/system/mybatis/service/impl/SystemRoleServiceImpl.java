package com.system.mybatis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.system.model.SysModule;
import com.system.model.SysRole;
import com.system.model.SysRoleFuncKey;
import com.system.mybatis.dao.SysRoleFuncMapper;
import com.system.mybatis.dao.SysRoleMapper;
import com.system.mybatis.service.ISysModuleService;
import com.system.mybatis.service.ISystemRoleService;

@Service
@Transactional(readOnly=true)
public class SystemRoleServiceImpl extends AbstractBusinessService<SysRole>
		implements ISystemRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysRoleFuncMapper sysRoleFuncMapper;

	@Autowired
	private ISysModuleService sysModuleServiceImpl;

	@Override
	public List<SysModule> findRoleModuleByRoleid(SysRole role) {

		List<SysModule> sysmocules = this.sysModuleServiceImpl
				.findMenusByRole(role);
		// TODO Auto-generated method stub
		return sysmocules;
	}

	@Override
	public BaseDao getDao() {
		return this.sysRoleMapper;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public int saveRole(SysRole sysRole) {

		int rows = 0;
		if (sysRole.getRoleid() == null || "".equals(sysRole.getRoleid())) {
			sysRole.setRoleid(UUIDUtil.getUUID());
			rows = this.sysRoleMapper.insertSelective(sysRole);
		} else
			rows = this.sysRoleMapper.updateByPrimaryKey(sysRole);

		return rows;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public int saveRolePermission(SysRole sysRole, String[] modids) {
		int rows = 0;

		String uuid = UUIDUtil.getUUID();
		if (sysRole.getRoleid() == null || "".equals(sysRole.getRoleid())) {
			sysRole.setRoleid(uuid);
			rows = this.sysRoleMapper.insertSelective(sysRole);
			
		} else{
			rows = this.sysRoleMapper.updateByPrimaryKey(sysRole);
			uuid = sysRole.getRoleid();
		}
		this.saveRolePer(modids, uuid);	
		return rows;
	}

	private void saveRolePer(String[] modids,String roleid){
		
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		
		criteria.andEqualTo("roleid", roleid);
	
		this.sysRoleFuncMapper.deleteByCondition(queryModel);
		for (String modid : modids) {
			if (modid==null||"".equals(modid.trim()))
				continue;
			SysRoleFuncKey sysRoleFunc = new SysRoleFuncKey();
			sysRoleFunc.setFuncid(modid);
			sysRoleFunc.setRoleid(roleid);
			this.sysRoleFuncMapper.insert(sysRoleFunc);
		}
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int delete(String[] ids) {
		int rows = 0;
		QueryModel queryModel = new QueryModel();
		
		for (String id : ids) {
			QueryModel.Criteria criteria = queryModel.createCriteria();
			
			criteria.andEqualTo("roleid", id);
			rows = rows + this.sysRoleMapper.deleteByPrimaryKey(id);
			this.sysRoleFuncMapper.deleteByCondition(queryModel);
		}
		return rows;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int delete(String id) {
		int rows = this.sysRoleMapper.deleteByPrimaryKey(id);
		return rows;
	}

	@Override
	public Object findRoleModule(SysRole role) {

		List<SysModule> modules = this.findRoleModuleByRoleid(role);

		return JSON.toJSON(modules);
	}
}
