package com.system.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.dao.Base.IDataMapper;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.model.QueryModel;
import com.system.model.DataIndexTree;
import com.system.model.SysIndexitem;

public interface SysIndexitemMapper extends IDataMapper<SysIndexitem>,IDataMapperByPage<SysIndexitem>  {
	
    int deleteByPrimaryKey(String indexid);

    int insert(SysIndexitem record);

    int insertSelective(SysIndexitem record);

    SysIndexitem selectByPrimaryKey(String indexid);

    int updateByPrimaryKeySelective(SysIndexitem record);

    int updateByPrimaryKey(SysIndexitem record);
    
    List<DataIndexTree> selectIndexTree(@Param("queryModel") QueryModel queryModel);
    
}