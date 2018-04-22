package com.museum.service.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.MuseumConstant;
import com.museum.dao.CulMainCulMapper;
import com.museum.model.CulCustomDataDisp;
import com.museum.model.CulIndexDetailData;
import com.museum.model.CulIndexDetailDataWithBLOBs;
import com.museum.model.CulMainCul;
import com.museum.model.CulShowCustom;
import com.museum.service.CulCustomDataDispService;
import com.museum.service.CulIndexDetailDataService;
import com.museum.service.CulMainCulService;
import com.system.model.SysIndexitem;
import com.system.mybatis.service.IIndexItemService;

@Service
@Transactional
public class CulMainCulServiceImpl extends AbstractBusinessService<CulMainCul> implements CulMainCulService {

    @Autowired
    private CulMainCulMapper culMainCulMapper;

    @Autowired
    private CulShowCustomServiceImpl culShowCustomServiceImpl;

    @Autowired
    private CulIndexDetailDataService culIndexDetailDataServiceImpl;

    @Autowired
    private CulCustomDataDispService culCustomDataDispServiceImpl;

    @Autowired
    private IIndexItemService indexItemService;

    public BaseDao getDao() {
	return this.culMainCulMapper;
    }

    public int delete(String recordId) {
	int rows = this.culMainCulMapper.deleteByPrimaryKey(recordId);
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int delete(String[] recordIds) {
	int rows = 0;
	QueryModel queryModel = new QueryModel();
	for (String id : recordIds) {
	    QueryModel.Criteria criteria = queryModel.createCriteria();
	    criteria.andEqualTo("CULID", id);
	    rows = rows + this.culMainCulMapper.deleteByPrimaryKey(id);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    public int save(CulMainCul record) {
	int rows = 0;
	if (record.getCulid() == null || record.getCulid() == "") {
	    String uuid = UUIDUtil.getUUID();
	    record.setCulid(uuid);
	    rows = this.culMainCulMapper.insert(record);
	} else {
	    rows = this.culMainCulMapper.updateByPrimaryKey(record);
	}
	this.logger.debug("rows: {}", rows);
	return rows;
    }

    @Override
    public int save(CulMainCul record, List<CulIndexDetailDataWithBLOBs> culIndexDatas) {

	int rows = 0;
	boolean isInsert = false;
	String uuid = record.getCulid();
	if (uuid == null || "".equals(uuid)) {
	    uuid = UUIDUtil.getUUID();
	    record.setCulid(uuid);
	    isInsert = true;
	}

	rows = this.compositeData(record, culIndexDatas);
	if (isInsert) {
	    rows = this.culMainCulMapper.insert(record);
	} else {
	    rows = this.culMainCulMapper.updateByPrimaryKey(record);
	}
	if (rows <= 0)
	    throw new RuntimeException("部分数据保存不成功");

	this.logger.debug("rows: {}", rows);
	return rows;
    }

    private int compositeData(CulMainCul record, List<CulIndexDetailDataWithBLOBs> culIndexDatas) {

	int rows = 0;
	List<CulShowCustom> customColumns = this.getCustomColumn();
	CulCustomDataDisp customDisp = this.culCustomDataDispServiceImpl.findObjectById(record.getCulid());
	if (customDisp == null) {
	    customDisp = new CulCustomDataDisp();
	}
	for (CulIndexDetailDataWithBLOBs detailData : culIndexDatas) {
	    detailData.setCulid(record.getCulid());

	    String indexid = detailData.getCulindexid();
	    CulShowCustom tmpCustom = new CulShowCustom();
	    tmpCustom.setIndexid(indexid);
	    // 主表默认的字段数据
	    if (MuseumConstant.BUSINESSINDEXA0102.equals(indexid))
		record.setCulname(detailData.getNormaldata());
	    else if (MuseumConstant.BUSINESSINDEXA0211.equals(indexid))
		record.setCultype(detailData.getSeltypevalue());
	    else if (MuseumConstant.BUSINESSINDEXB0401.equals(indexid))
		record.setCultype(detailData.getSeltypevalue());

	    rows = rows + this.culIndexDetailDataServiceImpl.save(detailData);
	    // 定制表显示数据
	    if (customColumns.contains(tmpCustom)) {
		int posIndex = customColumns.indexOf(tmpCustom);
		tmpCustom = customColumns.get(posIndex);
		String colName = tmpCustom.getColname();

		try {
		    Method method = CulCustomDataDisp.class.getMethod("set" + StringUtils.capitalize(colName),
			    String.class);
		    method.invoke(customDisp, detailData.getNormaldata());

		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }

	}
	rows = rows + this.culCustomDataDispServiceImpl.save(customDisp, record.getCulid());
	if (rows <= 0)
	    throw new RuntimeException("部分数据保存不成功");

	return rows;
    }

    @Override
    public List<CulShowCustom> getCustomColumn() {
	return this.culShowCustomServiceImpl.getDefaultCustomColumn();
    }

    @Override
    public List<CulIndexDetailData> getIndexData(String culid) {
	return this.culIndexDetailDataServiceImpl.getAllIndexData(culid);
    }

    @Override
    public Map<String, List<SysIndexitem>> getCustomEditIndex() {

	Map<String, List<SysIndexitem>> map = new HashMap<>();
	List<SysIndexitem> types = this.getCustomEditIndexType();
	for (SysIndexitem item : types) {
	    QueryModel queryModel = new QueryModel();
	    queryModel.createCriteria().andEqualTo("endflag", "1").andEqualTo("left(indexid,1)", item.getIndexid());
	    map.put(item.getIndexname(), this.indexItemService.findObjects(queryModel));
	}
	return map;
    }

    @Override
    public List<SysIndexitem> getCustomEditIndexType() {
	QueryModel queryModel = new QueryModel();
	queryModel.createCriteria().andEqualTo("superid", MuseumConstant.CULINDEXITEMROOT);
	queryModel.setOrderByClause("indexid");
	return this.indexItemService.findObjects(queryModel);
    }

}