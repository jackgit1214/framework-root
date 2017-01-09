package com.system.mybatis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.system.common.SysConstant;
import com.system.model.SysModule;
import com.system.model.SysRole;
import com.system.model.SysUser;
import com.system.mybatis.dao.SysModuleMapper;
import com.system.mybatis.service.ISysModuleService;

@Service
public class SysModuleServiceImpl extends AbstractBusinessService<SysModule>
		implements ISysModuleService {

	@Autowired
	private SysModuleMapper sysModuleMapper;

	@Override
	public BaseDao getDao() {
		// TODO Auto-generated method stub
		return this.sysModuleMapper;
	}

	@Override
	public List<SysModule> findMenusByParentId(String parentId) {

		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		queryModel.setOrderByClause("funorder");
		criteria.andEqualTo("IsInUse","1");
		if (null==parentId || "".equals(parentId ))
			criteria.andEqualTo("SuperModID", SysConstant.SYSDEFAULTROOTSUPPERID);
		else {
			criteria.andEqualTo("SuperModID", parentId);
		}
		List<SysModule> menus = this.findObjects(queryModel);
		return menus;

	}

	@Override
	public List<SysModule> findMenusByUser(SysUser user, String parentId) {

		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		queryModel.setOrderByClause("funorder");
		criteria.andEqualTo("IsInUse","1");
		if (null==parentId || "".equals(parentId ))
			criteria.andEqualTo("SuperModID", SysConstant.SYSDEFAULTROOTSUPPERID);
		else {
			criteria.andEqualTo("SuperModID", parentId);
		}
		List<SysModule> menus =null;
		if (SysConstant.SYSDEFAULTMANAGER.equals(user.getLogincode()))
			menus = this.findMenusByParentId(parentId);
		else 
			menus = this.sysModuleMapper.getModuleByUser(
				user.getUserid(), queryModel);
		return menus;
	}

	@Override
	public List<SysModule> findMenusByRole(SysRole role) {
		
		QueryModel queryModel = new QueryModel();
		//QueryModel.Criteria criteria = queryModel.createCriteria();
		queryModel.setOrderByClause("a.funorder");
		List<SysModule> modules = this.sysModuleMapper.getModuleByRole(role.getRoleid(), queryModel);
	

		return modules;
	}


}
