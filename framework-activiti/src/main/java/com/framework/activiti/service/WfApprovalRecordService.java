package com.framework.activiti.service;

import org.springframework.stereotype.Service;

import com.framework.activiti.model.WFApprovalRecord;
import com.framework.mybatis.service.IBusinessService;

@Service
public interface WfApprovalRecordService extends IBusinessService<WFApprovalRecord> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(WFApprovalRecord record);
}