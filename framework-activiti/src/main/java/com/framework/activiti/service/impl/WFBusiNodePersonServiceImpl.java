package com.framework.activiti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.activiti.dao.WFBusiNodePersonMapper;
import com.framework.activiti.model.WFBusiNodePerson;
import com.framework.activiti.service.WFBusiNodePersonService;
import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;

@Service
@Transactional
public class WFBusiNodePersonServiceImpl extends AbstractBusinessService<WFBusiNodePerson>
	implements WFBusiNodePersonService {
    @Autowired
    private WFBusiNodePersonMapper wFBusiNodePersonMapper;

    public BaseDao getDao() {
	return this.wFBusiNodePersonMapper;
    }

    public int delete(String recordId) {
	int rows = this.wFBusiNodePersonMapper.deleteByPrimaryKey(recordId);
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int delete(String[] recordIds) {
	int rows = 0;
	QueryModel queryModel = new QueryModel();
	for (String id : recordIds) {
	    QueryModel.Criteria criteria = queryModel.createCriteria();
	    criteria.andEqualTo("NODEPERSONID", id);
	    rows = rows + this.wFBusiNodePersonMapper.deleteByPrimaryKey(id);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int save(WFBusiNodePerson record) {
	int rows = 0;
	if (record.getNodepersonid() == null || record.getNodepersonid() == "") {
	    String uuid = UUIDUtil.getUUID();
	    record.setNodepersonid(uuid);
	    rows = this.wFBusiNodePersonMapper.insert(record);
	} else {
	    rows = this.wFBusiNodePersonMapper.updateByPrimaryKey(record);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }
}