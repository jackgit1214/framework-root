package com.framework.mybatis.dao.Base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;

public interface IDataMapperByPage<T> extends BaseDao {

    List<T> selectByCondition(@Param("queryModel") QueryModel queryModel,@Param("page") PageResult<T> page);
}
