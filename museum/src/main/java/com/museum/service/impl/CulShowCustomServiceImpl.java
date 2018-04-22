package com.museum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.CulShowCustomMapper;
import com.museum.model.CulShowCustom;
import com.museum.service.CulShowCustomService;

@Service
@Transactional
public class CulShowCustomServiceImpl extends AbstractBusinessService<CulShowCustom> implements CulShowCustomService {
    @Autowired
    private CulShowCustomMapper culShowCustomMapper;

    public BaseDao getDao() {
	return this.culShowCustomMapper;
    }

    public int delete(String recordId) {
	int rows = this.culShowCustomMapper.deleteByPrimaryKey(recordId);
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int delete(String[] recordIds) {
	int rows = 0;
	QueryModel queryModel = new QueryModel();
	for (String id : recordIds) {
	    QueryModel.Criteria criteria = queryModel.createCriteria();
	    criteria.andEqualTo("customId", id);
	    rows = rows + this.culShowCustomMapper.deleteByPrimaryKey(id);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int save(CulShowCustom record) {
	int rows = 0;
	if (record.getCustomid() == null || record.getCustomid() == "") {
	    String uuid = UUIDUtil.getUUID();
	    record.setCustomid(uuid);
	    rows = this.culShowCustomMapper.insert(record);
	} else {
	    rows = this.culShowCustomMapper.updateByPrimaryKey(record);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    @Override
    public List<CulShowCustom> getDefaultCustomColumn() {

	QueryModel queryModel = new QueryModel();
	QueryModel.Criteria criteria = queryModel.createCriteria();
	criteria.andEqualTo("customType", "1");
	queryModel.setOrderByClause("ShowSort");
	return this.findObjects(queryModel);
    }

    @Override
    public List<CulShowCustom> getCustomColumnByUnit(String unit) {

	QueryModel queryModel = new QueryModel();
	QueryModel.Criteria criteria = queryModel.createCriteria();
	criteria.andEqualTo("customType", "3").andEqualTo("customDataId", unit);
	queryModel.setOrderByClause("ShowSort");
	return this.findObjects(queryModel);
    }

    @Override
    public List<CulShowCustom> getCustomColumnByPerson(String personId) {

	QueryModel queryModel = new QueryModel();
	QueryModel.Criteria criteria = queryModel.createCriteria();
	criteria.andEqualTo("customType", "2").andEqualTo("customDataId", personId);
	queryModel.setOrderByClause("ShowSort");
	return this.findObjects(queryModel);
    }
}