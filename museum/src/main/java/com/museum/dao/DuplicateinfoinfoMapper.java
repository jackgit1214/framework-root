package com.museum.dao;

import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.dao.Base.IDataMapperCRUD;
import com.framework.mybatis.dao.Base.IDataMapperWithBlob;
import com.museum.model.Duplicateinfoinfo;

public interface DuplicateinfoinfoMapper extends
		IDataMapperByPage<Duplicateinfoinfo>,
		IDataMapperCRUD<Duplicateinfoinfo>,
		IDataMapperWithBlob<Duplicateinfoinfo> {
}