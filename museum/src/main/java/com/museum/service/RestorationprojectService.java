package com.museum.service;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.Restorationproject;
import java.util.List;

public interface RestorationprojectService extends IBusinessService<Restorationproject> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(Restorationproject record);
}