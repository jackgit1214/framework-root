package com.resources.dao;

import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.dao.Base.IDataMapperCRUD;
import com.framework.mybatis.dao.Base.IDataMapperWithBlob;
import com.resources.model.CommAttachments;

public interface AttachmentsMapper extends IDataMapperByPage<CommAttachments>,
		IDataMapperCRUD<CommAttachments>, IDataMapperWithBlob<CommAttachments> {
}