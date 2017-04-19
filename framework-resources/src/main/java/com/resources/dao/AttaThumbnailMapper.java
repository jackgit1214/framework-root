package com.resources.dao;

import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.dao.Base.IDataMapperCRUD;
import com.framework.mybatis.dao.Base.IDataMapperWithBlob;
import com.resources.model.CommAttaThumbnail;

public interface AttaThumbnailMapper extends
		IDataMapperByPage<CommAttaThumbnail>,
		IDataMapperCRUD<CommAttaThumbnail>,
		IDataMapperWithBlob<CommAttaThumbnail> {
}