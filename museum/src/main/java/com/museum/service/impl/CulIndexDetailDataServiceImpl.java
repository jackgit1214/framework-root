package com.museum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.CulIndexDetailDataMapper;
import com.museum.model.CulIndexDetailData;
import com.museum.model.CulIndexDetailDataWithBLOBs;
import com.museum.service.CulIndexDetailDataService;

@Service
@Transactional
public class CulIndexDetailDataServiceImpl extends AbstractBusinessService<CulIndexDetailData>
	implements CulIndexDetailDataService {
    @Autowired
    private CulIndexDetailDataMapper culIndexDetailDataMapper;

    public BaseDao getDao() {
	return this.culIndexDetailDataMapper;
    }

    public int delete(String recordId) {
	int rows = this.culIndexDetailDataMapper.deleteByPrimaryKey(recordId);
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int delete(String[] recordIds) {
	int rows = 0;
	QueryModel queryModel = new QueryModel();
	for (String id : recordIds) {
	    QueryModel.Criteria criteria = queryModel.createCriteria();
	    criteria.andEqualTo("dataDetailId", id);
	    rows = rows + this.culIndexDetailDataMapper.deleteByPrimaryKey(id);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int save(CulIndexDetailData record) {
	int rows = 0;
	if (record.getDatadetailid() == null || record.getDatadetailid() == "") {
	    String uuid = UUIDUtil.getUUID();
	    record.setDatadetailid(uuid);
	    rows = this.culIndexDetailDataMapper.insert(record);
	} else {
	    rows = this.culIndexDetailDataMapper.updateByPrimaryKey(record);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int save(CulIndexDetailDataWithBLOBs record) {
	int rows = 0;
	if (record.getDatadetailid() == null || record.getDatadetailid() == "") {
	    String uuid = UUIDUtil.getUUID();
	    record.setDatadetailid(uuid);
	    rows = this.culIndexDetailDataMapper.insert(record);
	} else {
	    rows = this.culIndexDetailDataMapper.updateByPrimaryKeyWithBLOBs(record);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    @Override
    public List<CulIndexDetailData> getAllIndexData(String culid) {
	QueryModel queryModel = new QueryModel();

	QueryModel.Criteria criteria = queryModel.createCriteria();
	if (null == culid)
	    criteria.andIsNull("culid");
	else
	    criteria.andEqualTo("t.culid", culid);

	queryModel.setOrderByClause("t.CULINDEXID");
	List<CulIndexDetailData> resutlData = this.culIndexDetailDataMapper.getAllIndexData(queryModel);

	return resutlData;
    }
}