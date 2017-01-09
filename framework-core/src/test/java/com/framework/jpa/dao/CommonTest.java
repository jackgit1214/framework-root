/**
 * 
 */
package com.framework.jpa.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.framework.DaoBaseTest.BaseTest;
import com.framework.jpa.dao.queryutil.EntityName;
import com.system.model.SysDept;


/**
 * @author lilj
 * 
 */

public class CommonTest extends BaseTest {

	@Autowired
	@EntityName(SysDept.class)
	@Qualifier("commonDao")
	private ICommonDao<SysDept> SysDept;


	
	/**
	 * Test method for {@link com.framework.dao.IQueryDao#findAll(int[])}.
	 */
	@Test
	public void testFindAll() {
		List<SysDept> sysDepts = this.SysDept.findAll(0, 180);
		assertEquals(3, sysDepts.size());
		
		
		for (SysDept aa :sysDepts){
			log.info(aa.getDeptid());
		}
		
	}
	
	
}
