package com.framework.resources.services.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.framework.resources.dao.AttachmentsMapper;
import com.framework.resources.model.CommAttachments;
import com.framework.resources.services.AttachmentsService;

@Service
@Transactional
public class AttachmentsServiceImpl extends
		AbstractBusinessService<CommAttachments> implements AttachmentsService {
	@Autowired
	private AttachmentsMapper attachmentsMapper;

	@Value("#{configProperties[uploadFilePath]}")
	public String uploadFilePath;

	// # 1为应用服务器路径 ，2为系统路径
	@Value("#{configProperties[uploadFilePathType]}")
	public String filePathType;

	public BaseDao getDao() {
		return this.attachmentsMapper;
	}

	public int delete(String recordId) {
		int rows = this.attachmentsMapper.deleteByPrimaryKey(recordId);
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int delete(String[] recordIds) {
		int rows = 0;
		QueryModel queryModel = new QueryModel();
		for (String id : recordIds) {
			QueryModel.Criteria criteria = queryModel.createCriteria();
			criteria.andEqualTo("attaID", id);
			rows = rows + this.attachmentsMapper.deleteByPrimaryKey(id);
		}
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int save(CommAttachments record) {
		int rows = 0;
		if (record.getAttaid() == null || record.getAttaid() == "") {
			String uuid = UUIDUtil.getUUID();
			record.setAttaid(uuid);
			rows = this.attachmentsMapper.insert(record);
		} else {
			rows = this.attachmentsMapper.updateByPrimaryKey(record);
		}
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public String attachmentHandle(HttpServletRequest request,
			CommAttachments record) throws IllegalStateException, IOException {

		String attId = UUIDUtil.getUUID();
		record.setAttaid(attId);
		this.fileHandle(request, record);
		return attId;
	}

	private String fileHandle(HttpServletRequest request,
			CommAttachments attachment) throws IllegalStateException,
			IOException {

		String attId = attachment.getAttaid();

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			List<MultipartFile> files = multiRequest.getFiles("files");
			Iterator<MultipartFile> iter = files.iterator();
			String sourcepath = request.getSession().getServletContext()
					.getRealPath("/");

			if ("1".equals(filePathType))
				sourcepath = sourcepath + uploadFilePath;
			else
				sourcepath = uploadFilePath;
			while (iter.hasNext()) {
				MultipartFile file = iter.next();
				// 如果文件名为空，不进行下面的保存操作
				if (file.getOriginalFilename().isEmpty()) {
					continue;
				}
				attachment.setFilename(file.getOriginalFilename());
				attachment.setFilesize(BigDecimal.valueOf(file.getSize()));
				String filename = attId + "_" + file.getOriginalFilename();
				attachment.setAttaname(filename);
				if (file != null) {
					String path = sourcepath + "/" + filename;
					File localFile = new File(path);
					attachment.setFilepath(uploadFilePath + "/" + filename);
					file.transferTo(localFile);
					this.save(attachment);
				}
			}
		}
		return attId;
	}
}