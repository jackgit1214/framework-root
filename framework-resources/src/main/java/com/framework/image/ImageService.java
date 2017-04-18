package com.framework.image;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.framework.image.handler.IImageHandler;

@Service
public class ImageService {

	private final int DEfAULT_WIDTH = 200;
	private final int DEfAULT_HEIGHT = 200;

	// # 1为linux，2为windows
	@Value("#{configProperties[imageHandleObject]}")
	public final String imageHandleObject = "iM4ImageHandler";

	// private IImageProvider imageProvider = new IOImageProvider();

	@Resource(name = imageHandleObject)
	private IImageHandler iImageHandler;

	// private IOImageHandler imageHandler = new IOImageHandler(imageProvider);

	/**
	 * 生成固定宽度，高度的缩略图
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public void getImageThumbnail(String sourceFile, String targetFile)
			throws IOException {
		this.getImageThumbnail(sourceFile, targetFile, this.DEfAULT_WIDTH,
				this.DEfAULT_HEIGHT);
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
