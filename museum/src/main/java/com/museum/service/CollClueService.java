package com.museum.service;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.CollClue;
import java.util.List;

public interface CollClueService extends IBusinessService<CollClue> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(CollClue record);
}