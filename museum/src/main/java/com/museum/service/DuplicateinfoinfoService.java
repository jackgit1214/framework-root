package com.museum.service;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.Duplicateinfoinfo;
import java.util.List;

public interface DuplicateinfoinfoService extends IBusinessService<Duplicateinfoinfo> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(Duplicateinfoinfo record);
}