package com.museum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.CulCustomDataDispMapper;
import com.museum.model.CulCustomDataDisp;
import com.museum.service.CulCustomDataDispService;

@Service
@Transactional
public class CulCustomDataDispServiceImpl extends AbstractBusinessService<CulCustomDataDisp>
	implements CulCustomDataDispService {
    @Autowired
    private CulCustomDataDispMapper culCustomDataDispMapper;

    public BaseDao getDao() {
	return this.culCustomDataDispMapper;
    }

    public int delete(String recordId) {
	int rows = this.culCustomDataDispMapper.deleteByPrimaryKey(recordId);
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int delete(String[] recordIds) {
	int rows = 0;
	QueryModel queryModel = new QueryModel();
	for (String id : recordIds) {
	    QueryModel.Criteria criteria = queryModel.createCriteria();
	    criteria.andEqualTo("CULID", id);
	    rows = rows + this.culCustomDataDispMapper.deleteByPrimaryKey(id);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int save(CulCustomDataDisp record) {
	int rows = 0;
	if (record.getCulid() == null || record.getCulid() == "") {
	    String uuid = UUIDUtil.getUUID();
	    record.setCulid(uuid);
	    rows = this.culCustomDataDispMapper.insert(record);
	} else {
	    rows = this.culCustomDataDispMapper.updateByPrimaryKey(record);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    @Override
    public int save(CulCustomDataDisp record, String culid) {
	int rows = 0;
	if (record.getCulid() == null || record.getCulid() == "") {

	    record.setCulid(culid);
	    rows = this.culCustomDataDispMapper.insert(record);
	} else {
	    rows = this.culCustomDataDispMapper.updateByPrimaryKey(record);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }
}