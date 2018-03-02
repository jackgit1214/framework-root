package com.framework.activiti.service;

import java.util.HashMap;
import java.util.Map;

import com.framework.activiti.listener.IProcessInstanceForm;
import com.framework.mybatis.service.AbstractBusinessService;

public abstract class AbstractActivitiService<T> extends AbstractBusinessService<T> implements IProcessInstanceForm {
    @Override
    public String getCurrentUserDeptId(String processInstanceId) {

	return "dept_01";
    }

    @Override
    public String getBusinessType(String processInstanceId) {
	// TODO Auto-generated method stub
	return "busi_01";
    }

    @Override
    public Map<String, Object> handleTask(String processInstanceId, String personId, Map<String, Object> param) {
	// TODO Auto-generated method stub
	Map<String, Object> map = new HashMap<>();
	map.put("input", param.get("input"));
	return map;
    }

    @Override
    public String startProcessInstance(String processKey, String formId) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public T getBusinessDataById(String id) {

	return this.findObjectById(id);
    }

}
