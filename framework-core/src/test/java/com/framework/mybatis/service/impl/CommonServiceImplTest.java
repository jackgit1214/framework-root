package com.framework.mybatis.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.daobasetest.BaseMybatisTest;
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
		Assert.assertEquals(10, modules.size());
	}

	@Test
	public void testFindAllObjects() {
		this.commonService.setBaseDao(sysModuleMapper);
		List<SysModule> modules = this.commonService.findAllObjects();

		Assert.assertNotNull(modules);
		Assert.assertEquals(10, modules.size());
	}

	@Override
	public void setUp() throws Exception {
		this.sysModuleMapper.deleteByCondition(null);

		List<SysModule> modules = this.sysModuleMapper.selectByCondition(null);

		Assert.assertEquals(0, modules.size());

		for (int i = 0; i < 10; i++) {
			SysModule module = new SysModule();
			module.setFuncid("id" + i);
			module.setFuncname("vvvvvvvvvvvvv");
			module.setSupermodid("1");
			module.setTargetDiv("maindiv");

			module.setIsinuse(i);
			module.setSystem(i);
			module.setFunorder(Byte.parseByte(String.valueOf(i)));
			module.setModdesc("ttttttttttt");
			this.sysModuleMapper.insert(module);
		}

	}
}
