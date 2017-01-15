package com.system.mybatis.service;

import java.util.List;

import com.framework.mybatis.service.IBusinessService;
import com.system.model.SysDepartmentTree;
import com.system.model.SysDept;

public interface ISysDeptService extends IBusinessService<SysDept> {

	public int updateDept(SysDept record);
	
	public int delete(String[] deptids);

	public int delete(String deptid);
	
	public List<SysDepartmentTree> getDeptBySupperId(String superid);
	

}
