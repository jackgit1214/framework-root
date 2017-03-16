package com.system.mybatis.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.daobasetest.BaseMybatisTest;
import com.framework.mybatis.model.QueryModel;
import com.system.model.DataIndexTree;

public class SysIndexitemMapperTest extends BaseMybatisTest {

	@Autowired
	private SysIndexitemMapper sysIndexitemMapper;

	@Test
	public void testSelectIndexTree() {
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("superid", "GB");
		queryModel.setOrderByClause("indexid");
		List<DataIndexTree> datas = this.sysIndexitemMapper
				.selectIndexTree(queryModel);

		Assert.assertNotNull(datas);
	}

}
