package com.framework.resources.services;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.resources.BaseMybatisTest;
import com.framework.resources.model.CommAttachments;

public class AttachmentsServiceTest extends BaseMybatisTest {

	@Autowired
	private AttachmentsService attachmentsServiceImpl;

	private String[] artid = new String[2];

	@Before
	public void setUp() throws Exception {
		super.setUp();
		CommAttachments attachments = new CommAttachments();
		attachments.setDataid("111111");
		attachments.setBusstype("1");
		attachments.setFilename("111111");
		attachments.setAttano(1);

		CommAttachments attachments1 = new CommAttachments();
		attachments1.setDataid("222222");
		attachments1.setBusstype("1");
		attachments1.setFilename("222222");
		attachments1.setAttano(1);
		this.attachmentsServiceImpl.save(attachments1);
		artid[0] = attachments1.getAttaid();
		this.attachmentsServiceImpl.save(attachments);
		artid[1] = attachments.getAttaid();
	}

	@Test
	public void testDeleteString() {
		this.attachmentsServiceImpl.delete(artid[0]);
	}

	@Test
	public void testDeleteStringArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

}
