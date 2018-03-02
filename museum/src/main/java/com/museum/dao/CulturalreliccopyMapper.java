package com.museum.dao;

import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.dao.Base.IDataMapperCRUD;
import com.framework.mybatis.dao.Base.IDataMapperWithBlob;
import com.museum.model.Culturalreliccopy;

public interface CulturalreliccopyMapper extends
		IDataMapperByPage<Culturalreliccopy>,
		IDataMapperCRUD<Culturalreliccopy>,
		IDataMapperWithBlob<Culturalreliccopy> {
}