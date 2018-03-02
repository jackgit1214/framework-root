package com.museum.service;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.Culturalreliccopy;
import java.util.List;

public interface CulturalreliccopyService extends IBusinessService<Culturalreliccopy> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(Culturalreliccopy record);
}