package com.system.mybatis.dao;

import java.util.List;

import org.junit.Assert;
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
		
		Assert.assertEquals(9,modules.size());
	}

}
