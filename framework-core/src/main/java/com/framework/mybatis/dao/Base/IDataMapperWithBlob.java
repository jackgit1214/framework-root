package com.framework.mybatis.dao.Base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.model.QueryModel;

public interface IDataMapperWithBlob<T> extends BaseDao {

    List<T> selectByConditionWithBLOBs(@Param("queryModel") QueryModel queryModel);
    
    int updateByConditionWithBLOBs(@Param("record") T record, @Param("queryModel") QueryModel queryModel);
    
    int updateByPrimaryKeyWithBLOBs(T record);
    
    T selectBlobByPrimaryKey(String id);
}
