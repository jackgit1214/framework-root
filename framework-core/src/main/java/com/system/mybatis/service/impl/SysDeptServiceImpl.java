package com.system.mybatis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.system.model.SysDepartmentTree;
import com.system.model.SysDept;
import com.system.mybatis.dao.SysDeptMapper;
import com.system.mybatis.dao.SysDeptUserMapper;
import com.system.mybatis.dao.SysIndexitemMapper;
import com.system.mybatis.service.ISysDeptService;

@Transactional(readOnly = true)
@Service
public class SysDeptServiceImpl extends AbstractBusinessService<SysDept> implements
		ISysDeptService {

	@Autowired
	public SysDeptUserMapper sysDeptUserMapper;
	
	@Autowired
	public SysDeptMapper sysDeptMapper;
	
	@Autowired
	public SysIndexitemMapper sysIndexitemMapper;
	
	
	@Override
	public int updateDept(SysDept record) {
		// TODO Auto-generated method stub
		int rows=0;
		if (record.getDeptid()==""||record.getDeptid()==null){
			String uuid = UUIDUtil.getUUID();
			record.setDeptid(uuid);
			rows = this.sysDeptMapper.insert(record);
		}else{
			rows = this.sysDeptMapper.updateByPrimaryKey(record);
		}
		return rows;
	}

	@Override
	public int delete(String[] deptids) {
		// TODO Auto-generated method stub
		
		int rows =0;
		QueryModel queryModel = new QueryModel();
		for (String id : deptids) {
			QueryModel.Criteria criteria = queryModel.createCriteria();

			criteria.andEqualTo("deptid", id);
			rows = rows + this.sysDeptMapper.deleteByPrimaryKey(id);
			this.sysDeptMapper.deleteByCondition(queryModel);
		}
		return rows;
	}

	@Override
	public int delete(String deptid) {
		int rows = this.sysDeptMapper.deleteByPrimaryKey(deptid);
		return rows;
	}

	@Override
	public List<SysDepartmentTree> getDeptBySupperId(String superid) {
		
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("superid",superid);
		queryModel.setOrderByClause("deptid");
		this.log.debug(queryModel.getCondition());
		
		List<SysDepartmentTree> datas = this.sysDeptMapper.selectDepartmentTree(queryModel);
		return datas;
	}

	@Override
	public BaseDao getDao() {
		// TODO Auto-generated method stub
		return this.sysDeptMapper;
	}

	
}
