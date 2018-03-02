package com.museum.service;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.Restorationinfo;
import java.util.List;

public interface RestorationinfoService extends IBusinessService<Restorationinfo> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(Restorationinfo record);
}