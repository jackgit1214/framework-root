package com.framework.mybatis.dao.Base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.model.QueryModel;

public interface IDataMapperCRUD<T> extends BaseDao {

	int insert(T record);

	int insertSelective(T record);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);

    int updateByConditionSelective(@Param("record") T record, @Param("queryModel") QueryModel queryModel);

    int updateByCondition(@Param("record") T record, @Param("queryModel") QueryModel queryModel);
	
    int deleteByCondition(@Param("queryModel")QueryModel queryModel);

	int deleteByPrimaryKey(String depotId);
    
    List<T> selectByCondition(@Param("queryModel") QueryModel queryModel);
    
    int countByCondition(@Param("queryModel")QueryModel queryModel);
    
    T selectByPrimaryKey(Object id);
}
