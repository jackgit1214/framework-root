package com.system.mybatis.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.daobasetest.BaseMybatisTest;
import com.framework.mybatis.model.QueryModel;
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

	@Test
	public void testQueryModelIn() {

		QueryModel queryModel = new QueryModel();

		queryModel.createCriteria().andIn("logincode",
				" SELECT logincode  FROM sys_user where userid = '0000' ");

		List<SysUser> users = this.systemUserServiceImpl
				.findObjects(queryModel);
		// fail("Not yet implemented");
	}
}
