package com.framework.mybatis.dao.Base;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.framework.mybatis.model.QueryModel;


public interface IDataMapper<T> extends BaseDao {

    int countByCondition(@Param("queryModel")QueryModel queryModel);

    int deleteByCondition(@Param("queryModel")QueryModel queryModel);

    List<T> selectByCondition(@Param("queryModel") QueryModel queryModel);
    
    int updateByConditionSelective(@Param("record") T record, @Param("queryModel") QueryModel queryModel);

    int updateByCondition(@Param("record") T record, @Param("queryModel") QueryModel queryModel);

    T selectByPrimaryKey(Object id);
	
    
}
