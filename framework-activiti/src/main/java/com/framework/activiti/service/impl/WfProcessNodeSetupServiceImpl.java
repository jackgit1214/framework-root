package com.framework.activiti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.activiti.dao.WfProcessNodeSetupMapper;
import com.framework.activiti.model.WfProcessNodeSetup;
import com.framework.activiti.service.WfProcessNodeSetupService;
import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;

@Service
@Transactional
public class WfProcessNodeSetupServiceImpl extends AbstractBusinessService<WfProcessNodeSetup>
	implements WfProcessNodeSetupService {
    @Autowired
    private WfProcessNodeSetupMapper wfProcessNodeSetupMapper;

    public BaseDao getDao() {
	return this.wfProcessNodeSetupMapper;
    }

    public int delete(String recordId) {
	int rows = this.wfProcessNodeSetupMapper.deleteByPrimaryKey(recordId);
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int delete(String[] recordIds) {
	int rows = 0;
	QueryModel queryModel = new QueryModel();
	for (String id : recordIds) {
	    QueryModel.Criteria criteria = queryModel.createCriteria();
	    criteria.andEqualTo("process_Setup_Id", id);
	    rows = rows + this.wfProcessNodeSetupMapper.deleteByPrimaryKey(id);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int save(WfProcessNodeSetup record) {
	int rows = 0;
	if (record.getProcessSetupId() == null || record.getProcessSetupId() == "") {
	    String uuid = UUIDUtil.getUUID();
	    record.setProcessSetupId(uuid);
	    rows = this.wfProcessNodeSetupMapper.insert(record);
	} else {
	    rows = this.wfProcessNodeSetupMapper.updateByPrimaryKey(record);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }
}