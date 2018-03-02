package com.framework.activiti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.activiti.dao.WFApprovalRecordMapper;
import com.framework.activiti.model.WFApprovalRecord;
import com.framework.activiti.service.WfApprovalRecordService;
import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;

@Service
@Transactional
public class WfApprovalRecordServiceImpl extends AbstractBusinessService<WFApprovalRecord>
	implements WfApprovalRecordService {
    @Autowired
    private WFApprovalRecordMapper wFApprovalRecordMapper;

    public BaseDao getDao() {
	return this.wFApprovalRecordMapper;
    }

    public int delete(String recordId) {
	int rows = this.wFApprovalRecordMapper.deleteByPrimaryKey(recordId);
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int delete(String[] recordIds) {
	int rows = 0;
	QueryModel queryModel = new QueryModel();
	for (String id : recordIds) {
	    QueryModel.Criteria criteria = queryModel.createCriteria();
	    criteria.andEqualTo("APPROVALID", id);
	    rows = rows + this.wFApprovalRecordMapper.deleteByPrimaryKey(id);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int save(WFApprovalRecord record) {
	int rows = 0;
	if (record.getApprovalid() == null || record.getApprovalid() == "") {
	    String uuid = UUIDUtil.getUUID();
	    record.setApprovalid(uuid);
	    rows = this.wFApprovalRecordMapper.insert(record);
	} else {
	    rows = this.wFApprovalRecordMapper.updateByPrimaryKey(record);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

}