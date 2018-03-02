package com.museum.service;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.AppraisalExpertInfo;
import java.util.List;

public interface AppraisalExpertInfoService extends IBusinessService<AppraisalExpertInfo> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(AppraisalExpertInfo record);
}