package com.system.mybatis.dao;

import com.framework.mybatis.dao.Base.IDataMapper;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.system.model.SysRoleFuncKey;

public interface SysRoleFuncMapper extends IDataMapper<SysRoleFuncKey>,IDataMapperByPage<SysRoleFuncKey> {
    int deleteByPrimaryKey(SysRoleFuncKey key);

    int insert(SysRoleFuncKey record);

    int insertSelective(SysRoleFuncKey record);
}