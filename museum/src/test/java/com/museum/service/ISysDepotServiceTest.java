package com.museum.service;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.museum.BaseMybatisTest;

public class ISysDepotServiceTest extends BaseMybatisTest {

	@Autowired
	private ISysDepotService sysDepotServiceImpl;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUpdateDepot() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteStringArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDepotBySupperId() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllObjects() {

		this.sysDepotServiceImpl.findAllObjects();
		// fail("Not yet implemented");
	}

	@Test
	public void testFindObjectById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindObjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindObjectsByPage() {
		fail("Not yet implemented");
	}

}
