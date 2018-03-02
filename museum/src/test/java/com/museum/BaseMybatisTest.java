/**
 * 
 */
package com.museum;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lilj
 *
 */
// ,"classpath:config/db/test-data.xml"
// 项目 中使用EnableWebMvc,此注解必须
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:config/application-museum.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("mybatis")
public abstract class BaseMybatisTest {

	protected final Log log = LogFactory.getLog(this.getClass());

	protected static boolean isExec = false;

	@Autowired
	private ApplicationContext applicationContext;

	private static int methodTestNum;

	private static int execNum;

	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before
	// 2
	public void setUp() throws Exception {

	}

	@BeforeTransaction
	public void verifyInitialDatabaseState() {
		// 1

		// 这里初始化的数据，将自动提交，必须在@AfterTransaction中手动删除

		/**
		 * 手工方法
		 */
		if (isExec)
			return;

		ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
		Resource resouce = applicationContext
				.getResource("classpath:config/db/testdata/initDB.sql");
		rdp.addScript(resouce);
		DataSource dataSource = (DataSource) this.applicationContext
				.getBean("dataSource");

		isExec = true;

		DatabasePopulatorUtils.execute(rdp, dataSource);

		// DataSourceInitializer di = new DataSourceInitializer();
		// di.setDatabasePopulator(rdp);
		// di.setDataSource(dataSource);
		// di.setEnabled(true);
		// di.afterPropertiesSet();
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method method : methods) {
			// @Test
			Test test = method.getAnnotation(Test.class);
			if (test != null)
				methodTestNum++;
		}

	}

	@AfterTransaction
	// 4
	public void verifyFinalDatabaseState() {

		/**
		 * 手工方法，删除测试数据
		 */
		if (execNum != methodTestNum)
			return;
		ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
		Resource resouce = applicationContext
				.getResource("classpath:config/db/testdata/delDB.sql");
		rdp.addScript(resouce);
		DataSource dataSource = (DataSource) this.applicationContext
				.getBean("dataSource");
		DatabasePopulatorUtils.execute(rdp, dataSource);
		isExec = false;

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	// 3
	public void tearDown() throws Exception {
		execNum++;
	}

}
