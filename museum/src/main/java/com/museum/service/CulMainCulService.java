package com.museum.service;

import java.util.List;
import java.util.Map;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.CulIndexDetailData;
import com.museum.model.CulIndexDetailDataWithBLOBs;
import com.museum.model.CulMainCul;
import com.museum.model.CulShowCustom;
import com.system.model.SysIndexitem;

public interface CulMainCulService extends IBusinessService<CulMainCul> {
    int delete(String recordId);

    int delete(String[] recordIds);

    /**
     * 
     * save:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @param record
     * @return
     * @since JDK 1.6
     */
    int save(CulMainCul record);

    List<CulShowCustom> getCustomColumn();

    /**
     * 
     * getCustomEditIndex:(取得页面编辑定制的指标). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @return
     * @since JDK 1.6
     */
    Map<String, List<SysIndexitem>> getCustomEditIndex();

    /**
     * getCustomEditIndexType:(定制的指标显示分类，用于当指标太多时，页面进行过滤). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @return
     * @since JDK 1.6
     */
    List<SysIndexitem> getCustomEditIndexType();

    /**
     * getIndexData:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @param culid
     * @return
     * @since JDK 1.6
     */
    List<CulIndexDetailData> getIndexData(String culid);

    /**
     * save:保存或更新前端页面的数据，. <br/>
     * TODO 主要用于编辑或修改文物数据时.<br/>
     * 
     * @author lilj
     * @param record
     * @param culIndexDatas
     * @return
     * @since JDK 1.6
     */
    int save(CulMainCul record, List<CulIndexDetailDataWithBLOBs> culIndexDatas);
}