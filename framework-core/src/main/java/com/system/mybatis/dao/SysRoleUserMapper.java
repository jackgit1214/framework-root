package com.system.mybatis.dao;

import com.framework.mybatis.dao.Base.IDataMapper;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.system.model.SysRoleUser;

public interface SysRoleUserMapper extends IDataMapper<SysRoleUser>,IDataMapperByPage<SysRoleUser> {
    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);
}