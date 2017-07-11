package com.system.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.dao.Base.IDataMapper;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.system.model.SysUser;

public interface SysUserMapper extends IDataMapper<SysUser>,
		IDataMapperByPage<SysUser> {
	int deleteByPrimaryKey(String userid);

	int insert(SysUser record);

	int insertSelective(SysUser record);

	int updateByPrimaryKeySelective(SysUser record);

	int updateByPrimaryKey(SysUser record);

	List<SysUser> selectUserByDeptId(@Param("deptid") String deptid);

	SysUser getAvatarByUserid(@Param("userid") String userid);

	int updateUserAvatar(SysUser record);
}