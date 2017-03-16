package com.system.mybatis.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.daobasetest.BaseMybatisTest;
import com.system.model.SysUser;

public class ISystemUserServiceTest extends BaseMybatisTest {

	@Autowired
	private ISystemUserService systemUserServiceImpl;

	@Test
	public void testSaveUser() throws Exception {
		SysUser user = new SysUser();
		user.setUserid("");
		user.setUsername("jlsdfjasdfadsf11111111111111111111111111111111111111111111111111111111111111111111111111111111");
		this.systemUserServiceImpl.saveUser(user);

	}

}
