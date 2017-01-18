package com.museum.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.museum.BaseMybatisTest;
import com.museum.model.SysDepot;
import com.museum.model.SysDepotTree;

public class SysDepotMapperTest extends BaseMybatisTest {

	@Autowired
	private SysDepotMapper sysDepotMapper;

	@Test
	public void testSelectDepotTree() {
		
		this.insertData();
		
		List<SysDepotTree> trees = this.sysDepotMapper.selectDepotTree(null);
		
		
		Assert.assertEquals(11,trees.size());
	}

	@Test
	public void testInsert() {

		SysDepot sysDepot = new SysDepot();

		sysDepot.setDepotId("123");
		sysDepot.setDepotName("玉器库");
		sysDepot.setDutyMan("张三");
		sysDepot.setNote("adfasfasdf");
		sysDepot.setDepotCode("vvvv");
		sysDepot.setSuperid("aaa");

		int rows = this.sysDepotMapper.insert(sysDepot);
		Assert.assertEquals(1, rows);
	}

	@Test
	public void testInsertSelective() {
		SysDepot sysDepot = new SysDepot();

		sysDepot.setDepotId("123");
		sysDepot.setDepotName("玉器库");
		sysDepot.setDutyMan("张三");
		sysDepot.setNote("adfasfasdf");
		sysDepot.setDepotCode("vvvv");
		sysDepot.setSuperid("aaa");

		int rows = this.sysDepotMapper.insert(sysDepot);
		Assert.assertEquals(1, rows);

	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		this.insertData();

		SysDepot sysDepot1 = new SysDepot();
		sysDepot1.setDepotName("玉器库更新");
		sysDepot1.setDepotId("123");
		this.sysDepotMapper.updateByPrimaryKeySelective(sysDepot1);

		SysDepot ss1 = this.sysDepotMapper.selectByPrimaryKey("123");
		Assert.assertEquals("玉器库更新", ss1.getDepotName());
		Assert.assertNotNull(ss1.getDepotCode());

	}

	@Test
	public void testUpdateByPrimaryKey() {

		this.insertData();

		SysDepot sysDepot1 = new SysDepot();
		sysDepot1.setDepotName("玉器库更新");
		sysDepot1.setDepotId("123");
		this.sysDepotMapper.updateByPrimaryKey(sysDepot1);

		SysDepot ss1 = this.sysDepotMapper.selectByPrimaryKey("123");
		Assert.assertEquals("玉器库更新", ss1.getDepotName());
		Assert.assertNull(ss1.getDepotCode());

	}

	@Test
	public void testUpdateByConditionSelective() {

		this.insertData();
		SysDepot sysDepot1 = new SysDepot();
		sysDepot1.setDepotName("玉器库更新");
		sysDepot1.setDepotId("123");
		this.sysDepotMapper.updateByPrimaryKey(sysDepot1);

		SysDepot ss1 = this.sysDepotMapper.selectByPrimaryKey("123");
		Assert.assertEquals("玉器库更新", ss1.getDepotName());
		Assert.assertNull(ss1.getDepotCode());

	}

	@Test
	public void testUpdateByCondition() {

		this.insertData();
		SysDepot sysDepot1 = new SysDepot();
		sysDepot1.setDepotName("玉器库更新");
		sysDepot1.setDepotId("123");
		this.sysDepotMapper.updateByPrimaryKey(sysDepot1);

		SysDepot ss1 = this.sysDepotMapper.selectByPrimaryKey("123");
		Assert.assertEquals("玉器库更新", ss1.getDepotName());
		Assert.assertNull(ss1.getDepotCode());

	}

	@Test
	public void testDeleteByCondition() {
		
		this.insertData();
		
		int rows = this.sysDepotMapper.countByCondition(null);
		Assert.assertEquals(11, rows);
		
		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria().andEqualTo("depot_id", "1238");
		
		rows = this.sysDepotMapper.deleteByCondition(queryModel);
		Assert.assertEquals(1, rows);
		
		rows = this.sysDepotMapper.deleteByCondition(null);
		Assert.assertEquals(10,rows);
		
	}

	@Test
	public void testDeleteByPrimaryKey() {
		
		this.insertData();
		
		int rows = this.sysDepotMapper.deleteByPrimaryKey("1232");
		Assert.assertEquals(1, rows);
		
	}

	@Test
	public void testSelectByConditionQueryModel() {
		
		this.insertData();
		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria()
				  .andLike("depot_id","123%");
		
		List<SysDepot> sysdepots  = this.sysDepotMapper.selectByCondition(queryModel);
		
		Assert.assertEquals(11, sysdepots.size());
		
	}


	@Test
	public void testSelectByConditionQueryModelPageResultOfT() {
		
		this.insertData();
		
		QueryModel queryModel = new QueryModel();
		
		queryModel.createCriteria()
				  .andLike("depot_id", "123%");
		
		queryModel.setOrderByClause("depot_id");
		PageResult<SysDepot> page = new  PageResult<SysDepot>(1,5);
		List<SysDepot> sysDepots = this.sysDepotMapper.selectByCondition(queryModel, page);
		
		Assert.assertEquals(5, sysDepots.size());
		
	}

	private void insertData() {
		
		SysDepot sysDepot = new SysDepot();

		sysDepot.setDepotId("123");
		sysDepot.setDepotName("玉器库");
		sysDepot.setDutyMan("张三");
		sysDepot.setNote("adfasfasdf");
		sysDepot.setDepotCode("vvvv");
		sysDepot.setSuperid("aaa");

		int rows = this.sysDepotMapper.insert(sysDepot);
		
		for (int i=0;i<10;i++){
			sysDepot = new SysDepot();

			sysDepot.setDepotId("123"+i);
			sysDepot.setDepotName("玉器库"+i);
			sysDepot.setDutyMan("张三"+i);
			sysDepot.setNote("adfasfasdf"+i);
			sysDepot.setDepotCode("vvvv"+i);
			sysDepot.setSuperid("aaa"+i);

			this.sysDepotMapper.insert(sysDepot);
		}
		
	}

	@Override
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("==============================================;");
		// 清除所有数据
		this.sysDepotMapper.deleteByCondition(null);

		int rows = this.sysDepotMapper.countByCondition(null);
		Assert.assertEquals(0, rows);
		super.setUp();
	}


}
