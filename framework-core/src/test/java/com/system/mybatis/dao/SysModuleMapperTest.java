package com.system.mybatis.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.DaoBaseTest.BaseMybatisTest;
import com.framework.mybatis.model.QueryModel;
import com.system.model.SysModule;

public class SysModuleMapperTest extends BaseMybatisTest {

	@Autowired
	private SysModuleMapper sysModuleMapper;
	
	@Test
	public void testSelectByConditionQueryModel() {
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		queryModel.setOrderByClause("funorder");
		criteria.andEqualTo("SuperModID", "1");
		
		List<SysModule> modules = this.sysModuleMapper.selectByCondition(queryModel);
		
		Assert.assertEquals(10,modules.size());
	}

	@Override
	public void setUp() throws Exception {
		this.sysModuleMapper.deleteByCondition(null);
		
		List<SysModule> modules = this.sysModuleMapper.selectByCondition(null);
		
		Assert.assertEquals(0, modules.size());
		
		for (int i=0;i<10;i++){
			SysModule module = new SysModule();
			module.setFuncid("id"+i);
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
