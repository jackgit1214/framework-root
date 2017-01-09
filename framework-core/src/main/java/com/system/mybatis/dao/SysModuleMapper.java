package com.system.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.dao.Base.IDataMapper;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.model.QueryModel;
import com.system.model.SysModule;

public interface SysModuleMapper extends IDataMapper<SysModule>,IDataMapperByPage<SysModule> {
	
	int deleteByPrimaryKey(String funcid);

	int insert(SysModule record);

	int insertSelective(SysModule record);

	int updateByPrimaryKeySelective(SysModule record);

	int updateByPrimaryKey(SysModule record);

	List<SysModule> getModuleByUser(@Param("userid")String userid,@Param("queryModel")QueryModel queryModel);
	
	List<SysModule> getModuleByRole(@Param("roleid")String roleid,@Param("queryModel")QueryModel queryModel);
	
	
}