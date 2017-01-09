package com.framework.mybatis.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.DaoBaseTest.BaseMybatisTest;
import com.system.model.SysModule;
import com.system.mybatis.dao.SysModuleMapper;

public class CommonServiceImplTest extends BaseMybatisTest {

	@Autowired
	private SysModuleMapper sysModuleMapper;
	
	@Autowired
	private CommonServiceImpl<SysModule> commonService;
	
	@Test
	public void testNotNull() {
		this.commonService.setBaseDao(sysModuleMapper);
		
		List<SysModule> modules = this.commonService.findAllObjects();
		Assert.assertNotNull(modules);
		Assert.assertEquals(modules.size(),19);
	}
	
	@Test
	public void testFindAllObjects() {
		this.commonService.setBaseDao(sysModuleMapper);
		List<SysModule> modules = this.commonService.findAllObjects();
		
		Assert.assertNotNull(modules);
		Assert.assertEquals(modules.size(),20);
	}

}
