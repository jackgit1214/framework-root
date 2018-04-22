package com.museum.service;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.CulCustomDataDisp;

public interface CulCustomDataDispService extends IBusinessService<CulCustomDataDisp> {
    int delete(String recordId);

    int delete(String[] recordIds);

    int save(CulCustomDataDisp record);

    /**
     * save:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @param record
     * @param culid
     * @return
     * @since JDK 1.6
     */
    int save(CulCustomDataDisp record, String culid);
}