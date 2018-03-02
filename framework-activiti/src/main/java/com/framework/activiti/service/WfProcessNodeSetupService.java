package com.framework.activiti.service;

import com.framework.activiti.model.WfProcessNodeSetup;
import com.framework.mybatis.service.IBusinessService;

public interface WfProcessNodeSetupService extends IBusinessService<WfProcessNodeSetup> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(WfProcessNodeSetup record);
}