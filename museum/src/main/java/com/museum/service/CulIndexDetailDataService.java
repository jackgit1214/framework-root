package com.museum.service;

import java.util.List;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.CulIndexDetailData;
import com.museum.model.CulIndexDetailDataWithBLOBs;

public interface CulIndexDetailDataService extends IBusinessService<CulIndexDetailData> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(CulIndexDetailData record);

    int save(CulIndexDetailDataWithBLOBs record);

    /**
     * getAllIndexData:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @param culid
     * @return
     * @since JDK 1.6
     */
    List<CulIndexDetailData> getAllIndexData(String culid);
}