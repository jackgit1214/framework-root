package com.framework.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.framework.common.util.UUIDUtil;
import com.framework.image.handler.IImageHandler;
import com.resources.dao.AttaThumbnailMapper;
import com.resources.model.CommAttaThumbnail;

@Service
public class ImageService {

	private final int DEfAULT_WIDTH = 200;
	private final int DEfAULT_HEIGHT = 200;

	// # 1为linux，2为windows
	@Value("#{configProperties[imageHandleObject]}")
	public final String imageHandleObject = "iM4ImageHandler";

	@Value("#{configProperties[thumbnailLevel]}")
	public String thumbnailLevel = "600,400";

	@Autowired
	private AttaThumbnailMapper attaThumbnailMapper;

	private int thumbnails[];

	@PostConstruct
	private void createThumbnails() {

		String[] tmpThumbs = thumbnailLevel.split(",");
		if (thumbnails == null) {
			thumbnails = new int[tmpThumbs.length];
		}
		int i = 0;
		for (String tmpt : tmpThumbs) {
			thumbnails[i] = Integer.parseInt(tmpt);
			i++;
		}
		// Arrays.sort(thumbnails);
	}

	// private IImageProvider imageProvider = new IOImageProvider();

	@Resource(name = imageHandleObject)
	private IImageHandler iImageHandler;

	// private IOImageHandler imageHandler = new IOImageHandler(imageProvider);

	/**
	 * 生成固定宽度，高度的缩略图
	 * 
	 * @param sourceFile
	 *            原图像路径
	 * @param dataid
	 *            原图像数据id
	 * @throws IOException
	 */
	public void getImageThumbnail(String sourceFile, String dataid)
			throws IOException {
		// this.getImageThumbnail(sourceFile, targetFile,
		// this.DEfAULT_WIDTH,this.DEfAULT_HEIGHT);
		try {
			List<String> files = iImageHandler.compressionImage(sourceFile,
					this.thumbnails, true);
			int level = 0;
			for (String tmpfile : files) {
				CommAttaThumbnail record = new CommAttaThumbnail();
				record.setTrumbnailid(UUIDUtil.getUUID());
				record.setAttaid(dataid);
				record.setRank(String.valueOf(level)); // 文件路径不存储，存储图像级别
				File file = new File(tmpfile);
				Map<String, String> fileInfo = iImageHandler
						.getImageInfo(tmpfile);

				record.setFilename(fileInfo.get(IImageConstant.IMAGE_FILENAME));

				record.setFilesize(fileInfo.get(IImageConstant.IMAGE_SIZE));
				record.setHeight(fileInfo.get(IImageConstant.IMAGE_HEIGHT));
				record.setWidth(fileInfo.get(IImageConstant.IMAGE_WIDTH));

				InputStream in = new FileInputStream(file);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024 * 4];
				int n = 0;
				while ((n = in.read(buffer)) != -1) {
					out.write(buffer, 0, n);
				}
				record.setFiledata(out.toByteArray());
				in.close();
				this.attaThumbnailMapper.insert(record);
				level++;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据指定宽高生成缩略图
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public void getImageThumbnail(String sourceFile, String targetFile,
			int width, int height) throws IOException {

		iImageHandler.resizeImage(sourceFile, targetFile, width, height);
	}

	/**
	 * 对图片进行剪切，根据指定的位置及宽，高
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @param intx
	 * @param inty
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public void getImageThumbnail(String sourceFile, String targetFile,
			int intx, int inty, int width, int height) throws IOException {
		iImageHandler.clipImage(sourceFile, targetFile, intx, inty, width,
				height);
	}

	public void markImage(String oriFileName, String targetFileName) {

	}

}
