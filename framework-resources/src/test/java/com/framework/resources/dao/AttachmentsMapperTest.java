package com.framework.resources.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.mybatis.model.QueryModel;
import com.framework.resources.BaseMybatisTest;
import com.resources.dao.AttachmentsMapper;
import com.resources.model.CommAttachments;

public class AttachmentsMapperTest extends BaseMybatisTest {

	@Autowired
	private AttachmentsMapper attachmentsMapper;

	@Test
	public void testInsert() {

		int rows = this.attachmentsMapper.countByCondition(null);
		Assert.assertEquals(2, rows);

	}

	@Test
	public void testUpdateByPrimaryKey() {
		CommAttachments attachments = new CommAttachments();
		attachments.setAttaid("111111");
		attachments.setDataid("2222");
		attachments.setBusstype("1");
		attachments.setFilename("2222");
		attachments.setAttano(1);
		int rows = this.attachmentsMapper.updateByPrimaryKey(attachments);

		Assert.assertEquals(1, rows);
		CommAttachments record = this.attachmentsMapper
				.selectByPrimaryKey("111111");

		Assert.assertEquals(attachments.getFilename(), record.getFilename());

	}

	@Test
	public void testDeleteByPrimaryKey() {
		int rows = this.attachmentsMapper.deleteByPrimaryKey("111111");
		Assert.assertEquals(1, rows);
		rows = this.attachmentsMapper.countByCondition(null);
		Assert.assertEquals(1, rows);
	}

	@Test
	public void testSelectByConditionQueryModel() {

		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria().andEqualTo("filename", "222222");
		List<CommAttachments> datas = this.attachmentsMapper
				.selectByCondition(queryModel);
		Assert.assertEquals(1, datas.size());
	}

	@Test
	public void testSelectByConditionQueryModelOr() {

		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria().andEqualTo("filename", "222222");
		queryModel.or().andEqualTo("filename", "111111");
		List<CommAttachments> datas = this.attachmentsMapper
				.selectByCondition(queryModel);
		Assert.assertEquals(2, datas.size());
	}

	@Override
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		CommAttachments attachments = new CommAttachments();
		attachments.setAttaid("111111");
		attachments.setDataid("111111");
		attachments.setBusstype("1");
		attachments.setFilename("111111");
		attachments.setAttano(1);

		CommAttachments attachments1 = new CommAttachments();
		attachments1.setAttaid("222222");
		attachments1.setDataid("222222");
		attachments1.setBusstype("1");
		attachments1.setFilename("222222");
		attachments1.setAttano(1);
		this.attachmentsMapper.insert(attachments);
		this.attachmentsMapper.insert(attachments1);
	}

	@Override
	public void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

}
