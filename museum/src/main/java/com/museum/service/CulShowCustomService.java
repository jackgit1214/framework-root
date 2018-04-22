package com.museum.service;

import java.util.List;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.CulShowCustom;

public interface CulShowCustomService extends IBusinessService<CulShowCustom> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(CulShowCustom record);

    List<CulShowCustom> getDefaultCustomColumn();

    List<CulShowCustom> getCustomColumnByUnit(String unit);

    List<CulShowCustom> getCustomColumnByPerson(String personId);
}