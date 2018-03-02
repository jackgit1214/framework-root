package com.framework.activiti.service;

import java.util.List;

import com.framework.activiti.listener.IProcessInstanceForm;
import com.framework.activiti.model.WfProcessInstanceForm;
import com.framework.mybatis.service.IBusinessService;

public interface WfProcessInstanceFormService extends IBusinessService<WfProcessInstanceForm>, IProcessInstanceForm {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(WfProcessInstanceForm record);

    List<WfProcessInstanceForm> getTaskListByPersonId(String processDefId, String personId);
}