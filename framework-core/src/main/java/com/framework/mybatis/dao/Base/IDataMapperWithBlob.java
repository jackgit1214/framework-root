package com.framework.mybatis.dao.Base;

import java.util.List;

import com.framework.mybatis.util.PageResult;
import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.model.QueryModel;

public interface IDataMapperWithBlob<T> extends BaseDao {

    List<T> selectByConditionWithBLOBs(@Param("queryModel") QueryModel queryModel,@Param("page") PageResult<T> page);

    int updateByConditionWithBLOBs(@Param("record") T record, @Param("queryModel") QueryModel queryModel);

    int updateByPrimaryKeyWithBLOBs(T record);

    T selectBlobByPrimaryKey(String id);
}
