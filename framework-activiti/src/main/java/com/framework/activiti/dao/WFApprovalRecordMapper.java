package com.framework.activiti.dao;

import com.framework.activiti.model.WFApprovalRecord;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.dao.Base.IDataMapperCRUD;
import com.framework.mybatis.dao.Base.IDataMapperWithBlob;

public interface WFApprovalRecordMapper extends IDataMapperByPage<WFApprovalRecord>, IDataMapperCRUD<WFApprovalRecord>,
	IDataMapperWithBlob<WFApprovalRecord> {
}