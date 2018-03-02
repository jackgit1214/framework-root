package com.framework.activiti.service;

import com.framework.activiti.model.WFBusiNodePerson;
import com.framework.mybatis.service.IBusinessService;

public interface WFBusiNodePersonService extends IBusinessService<WFBusiNodePerson> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(WFBusiNodePerson record);
}