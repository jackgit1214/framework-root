package com.museum.service;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.Restorationreceipt;
import java.util.List;

public interface RestorationreceiptService extends IBusinessService<Restorationreceipt> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(Restorationreceipt record);
}