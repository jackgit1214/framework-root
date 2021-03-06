/*
 * 
 * 应用@value的方式
 * <bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer">
	        <property name="properties" value="classpath*:config/config.properties"/>
	   </bean>
 */
package com.resources.services.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.framework.common.util.UUIDUtil;
import com.framework.image.ImageService;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.resources.ResConstant;
import com.resources.dao.AttaThumbnailMapper;
import com.resources.dao.AttachmentsMapper;
import com.resources.model.CommAttaThumbnail;
import com.resources.model.CommAttachments;
import com.resources.services.AttachmentsService;
import com.system.common.SysConstant;

@Service
@Transactional
public class AttachmentsServiceImpl extends
		AbstractBusinessService<CommAttachments> implements AttachmentsService {

	@Autowired
	private AttachmentsMapper attachmentsMapper;
	@Autowired
	private AttaThumbnailMapper attaThumbnailMapper;

	@Autowired
	private ImageService imageService;

	@Value("#{configProperties[uploadFilePath]}")
	public String uploadFilePath;

	// # 1为应用服务器相对路径 ，2为系统路径
	@Value("#{configProperties[uploadFilePathType]}")
	public String filePathType;

	// 是否对图片进行处理压缩,压缩时的缩略图路径
	@Value("#{configProperties[isHandleImage]}")
	public boolean isHandleImage = false;

	// 是否存存储文件流信息
	@Value("#{configProperties[isSaveFileStream]}")
	public boolean isSaveFileStream = false;

	// 文件分类方法，是业务ID,还是文件类型
	@Value("#{configProperties[isClassified]}")
	public boolean isClassified = true;

	// 0以数据类型进行区分
	@Value("#{configProperties[classifiedType]}")
	public int classifiedType = SysConstant.CLASSIFIEDTYPE_DATATYPE;

	private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	//

	public String getResource(String attaid) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String storePath = request.getSession().getServletContext()
				.getRealPath("/");// 存储路径
		CommAttachments attachment = this.findObjectById(attaid);
		String filePath = "";
		if ("1".equals(this.filePathType)) {
			filePath = storePath + attachment.getFilepath();
		} else {
			filePath = attachment.getFilepath();
		}

		return filePath;

	}

	public byte[] getResource(String attaid, String permission) {

		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria().andEqualTo("attaId", attaid)
				.andEqualTo("rank", permission);
		List<CommAttaThumbnail> attaThumbnails = this.attaThumbnailMapper
				.selectByConditionWithBLOBs(queryModel);
		if (attaThumbnails.size() == 0)
			return null;
		byte[] filedata = attaThumbnails.get(0).getFiledata();

		return filedata;

	}

	public BaseDao getDao() {
		return this.attachmentsMapper;
	}

	public int delete(String recordId) {
		int rows = this.attachmentsMapper.deleteByPrimaryKey(recordId);
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public Map<String, Integer> delete(String[] recordIds) {
		int rows = 0, fileNum = 0;
		QueryModel queryModel = new QueryModel();

		Map<String, Integer> rtnMap = new HashMap<String, Integer>();
		for (String id : recordIds) {
			QueryModel.Criteria criteria = queryModel.createCriteria();
			criteria.andEqualTo("attaID", id);
			CommAttachments attachment = this.attachmentsMapper
					.selectByPrimaryKey(id);

			boolean isSuccess = this.delFileHandle(attachment);
			if (isSuccess) {
				fileNum++;
			}
			this.attaThumbnailMapper.deleteByCondition(queryModel);
			rows = rows + this.attachmentsMapper.deleteByPrimaryKey(id);
		}
		rtnMap.put("successRows", rows);
		rtnMap.put("filerow", fileNum);
		this.logger.debug("rows: {}", rows);
		return rtnMap;
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

	public List<String> attachmentHandle(HttpServletRequest request,
			CommAttachments record) {

		List<String> attIds = null;

		try {
			attIds = this.fileHandle(request, record);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return attIds;
	}

	/**
	 * 删除附件对应的文件
	 * 
	 * @param attachment
	 *            附件信息
	 * @return
	 */
	private boolean delFileHandle(CommAttachments attachment) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		String storePath = request.getSession().getServletContext()
				.getRealPath("/");// 存储路径

		if ("1".equals(filePathType)) // 数据库中存放的路径
			storePath = storePath + attachment.getFilepath();
		else
			storePath = attachment.getFilepath();

		File localFile = new File(storePath);
		boolean isSuccess = localFile.delete();
		this.logger.debug(storePath);
		return isSuccess;
	}

	/**
	 * 上传文件时，文件存储处理
	 * 
	 * @param request
	 * @param attachment
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private List<String> fileHandle(HttpServletRequest request,
			CommAttachments attachment) throws IllegalStateException,
			IOException {
		List<String> attIds = new ArrayList<String>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (!multipartResolver.isMultipart(request)) {
			throw new RuntimeException("非法的附件上传.....");
		}

		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		String isDefault = multiRequest.getParameter("isDefault");
		String[] defaults = isDefault.split(",");
		List<MultipartFile> files = new ArrayList<MultipartFile>();

		MultiValueMap<String, MultipartFile> fileMaps = multiRequest
				.getMultiFileMap();

		Set<Entry<String, List<MultipartFile>>> set = fileMaps.entrySet();
		Iterator<Entry<String, List<MultipartFile>>> setiter = set.iterator();
		while (setiter.hasNext()) {
			Entry<String, List<MultipartFile>> entry = setiter.next();
			files.addAll(entry.getValue());
		}

		Iterator<MultipartFile> iter = files.iterator();
		String storePath = request.getSession().getServletContext()
				.getRealPath("/");// 存储路径

		storePath = storePath + uploadFilePath;
		int i = 0;
		while (iter.hasNext()) {
			CommAttachments record = new CommAttachments();

			MultipartFile file = iter.next();

			// 如果文件名为空，不进行下面的保存操作
			if (file.getOriginalFilename().isEmpty()) {
				continue;
			}

			// 复制业务数据，包含业务类型、业务数据ID，业务附加数据、附件类型
			BeanUtils.copyProperties(attachment, record);
			String attId = UUIDUtil.getUUID();
			record.setAttaid(attId);
			if (record.getAttano() == null)
				record.setAttano(0);

			if (this.isSaveFileStream) // 存储文件流
				record.setFileblob(file.getBytes());

			record.setFilename(file.getOriginalFilename());
			record.setFilesize(BigDecimal.valueOf(file.getSize()));
			String filename = attId + "_" + file.getOriginalFilename();
			record.setAttaname(filename);
			record.setIsDefault(defaults[i]);
			// record.setAttatype(attatype);
			int pos = filename.lastIndexOf(".");
			// 取文件扩展名
			String fileExtension = filename.substring(pos + 1);

			record.setAttatype(this.ExpandedNameHandle(fileExtension));

			if (file != null) {
				String path = "";
				String sourcepath = uploadFilePath;
				// 根据分类方法创建子目录
				if (this.isClassified) {

					if (this.classifiedType == 0) { // 数据业务分为类，根据ID
						path = storePath + "/" + attachment.getDataid();
						sourcepath = sourcepath + "/" + attachment.getDataid();
					} else { // 附件扩展名分类
						sourcepath = sourcepath + "/" + fileExtension;
						path = storePath + "/" + fileExtension;
					}
					File dirFile = new File(path);
					if (!dirFile.exists())
						dirFile.mkdirs();
				}
				// String tumbFilepath = path + "/tumb" + filename;
				path = path + "/" + filename;

				File localFile = new File(path);
				file.transferTo(localFile); // 存储文件
				if (ResConstant.RES_IMAGE == Integer.parseInt(record
						.getAttatype()))
					this.handleImage(path, attId); // 为图片时进行图片压缩处理

				if ("1".equals(filePathType)) // 数据库中存放的路径
					sourcepath = sourcepath + "/" + filename; // 项目相对路径
				else
					sourcepath = storePath + "/" + filename; // 绝对路径

				record.setFilepath(sourcepath);
				this.attachmentsMapper.insert(record);
				// this.save(record);
			}
			attIds.add(attId);
			i++;
		}

		return attIds;
	}

	private void handleImage(final String sourcePath, final String dataid) {

		cachedThreadPool.execute(new Runnable() {
			public void run() {
				try {
					imageService.getImageThumbnail(sourcePath, dataid);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}

	/**
	 * 根据文件名，区分类型
	 * 
	 * @param filename
	 * @return
	 */
	private String ExpandedNameHandle(String fileExtname) {

		// Pattern pDoc = Pattern.compile(docExt);
		// Pattern pVideo = Pattern.compile(videoExt);
		// Pattern pAudio = Pattern.compile(audioExt);
		// Pattern pImage = Pattern.compile(imageExt);
		String fileType = "4";
		if (Pattern.matches(docExt, fileExtname))
			fileType = String.valueOf(ResConstant.RES_DOC);
		if (Pattern.matches(videoExt, fileExtname))
			fileType = String.valueOf(ResConstant.RES_VIDEO);
		if (Pattern.matches(audioExt, fileExtname))
			fileType = String.valueOf(ResConstant.RES_AUDIO);
		if (Pattern.matches(imageExt, fileExtname))
			fileType = String.valueOf(ResConstant.RES_IMAGE);

		return fileType;

	}
}