package com.framework.activiti.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.activiti.dao.WfProcessInstanceFormMapper;
import com.framework.activiti.model.WfProcessInstanceForm;
import com.framework.activiti.service.WfProcessInstanceFormService;
import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;

@Service
@Transactional
public class WfProcessInstanceFormServiceImpl extends AbstractBusinessService<WfProcessInstanceForm>
	implements WfProcessInstanceFormService {
    @Autowired
    private WfProcessInstanceFormMapper wfProcessInstanceFormMapper;

    public BaseDao getDao() {
	return this.wfProcessInstanceFormMapper;
    }

    public int delete(String recordId) {
	int rows = this.wfProcessInstanceFormMapper.deleteByPrimaryKey(recordId);
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int delete(String[] recordIds) {
	int rows = 0;
	QueryModel queryModel = new QueryModel();
	for (String id : recordIds) {
	    QueryModel.Criteria criteria = queryModel.createCriteria();
	    criteria.andEqualTo("id", id);
	    rows = rows + this.wfProcessInstanceFormMapper.deleteByPrimaryKey(id);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int save(WfProcessInstanceForm record) {
	int rows = 0;
	if (record.getId() == null || record.getId() == "") {
	    String uuid = UUIDUtil.getUUID();
	    record.setId(uuid);
	    rows = this.wfProcessInstanceFormMapper.insert(record);
	} else {
	    rows = this.wfProcessInstanceFormMapper.updateByPrimaryKey(record);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

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
    public Object getBusinessDataById(String id) {

	return this.findObjectById(id);
    }

    @Override
    public List<WfProcessInstanceForm> getTaskListByPersonId(String processDefId, String personId) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void fillFormInstanceId(String processInstanceId, String formId) {
	WfProcessInstanceForm form = this.findObjectById(formId);
	form.setProinsid(processInstanceId);
	this.save(form);

    }

}