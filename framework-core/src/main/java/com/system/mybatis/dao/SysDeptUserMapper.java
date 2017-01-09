package com.system.mybatis.dao;

import com.framework.mybatis.dao.Base.IDataMapper;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.system.model.SysDeptUser;

public interface SysDeptUserMapper extends IDataMapper<SysDeptUser>,IDataMapperByPage<SysDeptUser> {
	
    int deleteByPrimaryKey(String id);

    int insert(SysDeptUser record);

    int insertSelective(SysDeptUser record);

    SysDeptUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDeptUser record);

    int updateByPrimaryKey(SysDeptUser record);
}