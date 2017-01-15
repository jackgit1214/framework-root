package com.system.mybatis.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.DaoBaseTest.BaseMybatisTest;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.system.model.SysDept;


public class SysDeptMapperTest extends BaseMybatisTest {

	@Autowired
	private SysDeptMapper sysDeptMapper;
	
	@Test
	public void testDeleteByPrimaryKey() {
		this.sysDeptMapper.deleteByPrimaryKey("0001");
		int rows = this.sysDeptMapper.countByCondition(null);
		Assert.assertEquals(2, rows);
	}

	@Test
	public void testFindSysdept() {
		List<SysDept> sysDepts = this.sysDeptMapper.selectByCondition(null);
		Assert.assertEquals(3,sysDepts.size());
	}

	@Test
	public void testSelectSysdeptByPage() {
		PageResult<SysDept> page = new PageResult<SysDept>(2, 1);
		List<SysDept> sysdepts = this.sysDeptMapper.selectByCondition(null, page);
		Assert.assertEquals(1, sysdepts.size());
		Assert.assertEquals(3, page.getTotalSize());
		SysDept sysDept = sysdepts.get(0);
		Assert.assertEquals("测试部门1", sysDept.getDeptName());
	}

	@Test
	public void testInsert() {
		SysDept sysDept = new SysDept();
		sysDept.setDeptid("0004");
		sysDept.setDeptName("插入部门");
		sysDept.setSortid(Short.valueOf("0"));
		this.sysDeptMapper.insert(sysDept);
		int rows = this.sysDeptMapper.countByCondition(null);
		Assert.assertEquals(4, rows);
	}

	@Test
	public void testCountByCondition() {
		int rows = this.sysDeptMapper.countByCondition(null);
		Assert.assertEquals(3, rows);
		
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("dept_name", "测试部门");
		rows = this.sysDeptMapper.countByCondition(queryModel);
		Assert.assertEquals(1,rows);
	}

	@Test
	public void testDeleteByCondition() {
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("dept_name", "测试部门");
		this.sysDeptMapper.deleteByCondition(queryModel);
		int rows = this.sysDeptMapper.countByCondition(null);
		Assert.assertEquals(2,rows);
	}

	@Test
	public void testSelectByConditionQueryModel() {
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("dept_name", "测试部门");
		List<SysDept> sysdepts = this.sysDeptMapper.selectByCondition(queryModel);
		Assert.assertEquals(1,sysdepts.size());
	}
	
	@Test
	public void testUpdateByCondition() {
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("dept_name", "测试部门");
		SysDept sysDept = this.sysDeptMapper.selectByPrimaryKey("0000");
		sysDept.setDeptName("更改部门名称");
		this.sysDeptMapper.updateByCondition(sysDept, queryModel);
		
		SysDept sysDept1 = this.sysDeptMapper.selectByPrimaryKey("0000");
		Assert.assertEquals("更改部门名称", sysDept1.getDeptName());
	}

	@Test
	public void testUpdateByConditionSelective() {
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("dept_name", "测试部门");
		SysDept sysDept = this.sysDeptMapper.selectByPrimaryKey("0000");
		sysDept.setDeptName("更改部门名称");
		this.sysDeptMapper.updateByConditionSelective(sysDept, queryModel);
		
		SysDept sysDept1 = this.sysDeptMapper.selectByPrimaryKey("0000");
		Assert.assertEquals("更改部门名称", sysDept1.getDeptName());
	}

	
	
	@Test
	public void testUpdateByPrimaryKeySelective() {

		SysDept sysDept = this.sysDeptMapper.selectByPrimaryKey("0000");
		sysDept.setDeptName("更改部门名称");
		this.sysDeptMapper.updateByPrimaryKeySelective(sysDept);
		
		SysDept sysDept1 = this.sysDeptMapper.selectByPrimaryKey("0000");
		Assert.assertEquals("更改部门名称", sysDept1.getDeptName());
	}
	
	@Test
	public void testSelectByPrimaryKeyObject() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSelectByConditionQueryModelPageResultOfT() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testselectDepotTree() {
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		
		criteria.andEqualTo("a.superid", "XXX");
		
		List list =this.sysDeptMapper.selectDepotTree(queryModel);
		Assert.assertNotNull(list);
		
	}
	

}
