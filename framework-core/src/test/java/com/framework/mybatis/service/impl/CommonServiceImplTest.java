package com.framework.mybatis.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	public static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();

	@Test
	public void testSeqenceName() {

		ExecutorService es = Executors.newCachedThreadPool();

		for (int i = 0; i < 1; i++) {
			es.execute(new Runnable() {

				@Override
				public void run() {
					int nextVal = commonService.getNextVal("t_test1");

					if (!map.containsKey(Integer.toString(nextVal))) {
						map.put(Integer.toString(nextVal),
								Integer.toString(nextVal));
					} else {

						System.out.println(nextVal + " is exists.");
					}
				}
			});
		}

		try {

			File ftest = new File("c:/test.txt");
			// ftest.
			boolean isexis = ftest.delete();
			Thread.sleep(100);
			System.out.println("--------------------------------------------");
			for (Map.Entry<String, String> entry : map.entrySet()) {

				System.out.println("key= " + entry.getKey() + " and value= "
						+ entry.getValue());
			}
			System.out.println(map.size());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testSeqenceName1() {
		@SuppressWarnings("unused")
		int nextVal = commonService.getNextVal("t_test1");
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
