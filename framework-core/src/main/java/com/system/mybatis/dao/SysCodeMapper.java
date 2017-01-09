package com.system.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.dao.Base.IDataMapper;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.model.QueryModel;
import com.system.model.SysCode;
import com.system.model.SysCodeTree;

public interface SysCodeMapper extends IDataMapper<SysCode>,IDataMapperByPage<SysCode> {
    int deleteByPrimaryKey(String codeid);

    int insert(SysCode record);

    int insertSelective(SysCode record);

    SysCode selectByPrimaryKey(String codeid);

    int updateByPrimaryKeySelective(SysCode record);

    int updateByPrimaryKey(SysCode record);

	List<SysCodeTree> selectCodeTree(@Param("queryModel") QueryModel queryModel);
}