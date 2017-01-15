package com.system.mybatis.dao;

import org.springframework.stereotype.Repository;

import com.framework.mybatis.dao.Base.IDataMapper;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.system.model.SysLog;

@Repository
public interface SysLogMapper extends IDataMapper<SysLog>,IDataMapperByPage<SysLog>  {
    int deleteByPrimaryKey(String logid);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(String logid);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
}