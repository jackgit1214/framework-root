package com.system.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.dao.Base.IDataMapper;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.model.QueryModel;
import com.system.model.SysRole;

public interface SysRoleMapper extends IDataMapper<SysRole>,IDataMapperByPage<SysRole> {
	
    int deleteByPrimaryKey(String roleid);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    List<SysRole> getSysRoleByUser(@Param("userid")String userid,@Param("queryModel")QueryModel queryModel);
    
}