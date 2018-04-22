package com.museum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.model.QueryModel;
import com.museum.model.CulIndexDetailData;
import com.museum.model.CulIndexDetailDataWithBLOBs;

public interface CulIndexDetailDataMapper extends com.framework.mybatis.dao.Base.IDataMapperByPage<CulIndexDetailData>,
	com.framework.mybatis.dao.Base.IDataMapperCRUD<CulIndexDetailData>,
	com.framework.mybatis.dao.Base.IDataMapperWithBlob<CulIndexDetailDataWithBLOBs> {

    List<CulIndexDetailData> getAllIndexData(@Param("queryModel") QueryModel queryModel);

}