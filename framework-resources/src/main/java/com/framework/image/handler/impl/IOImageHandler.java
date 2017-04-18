package com.framework.image.handler.impl;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.framework.image.IImageConstant;
import com.framework.image.ImageStrategy;
import com.framework.image.handler.IImageHandler;
import com.framework.image.provider.IImageProvider;
import com.framework.image.provider.impl.IOImageProvider;

public class IOImageHandler implements IImageHandler {

	private IImageProvider imageProvider;

	public IOImageHandler(IImageProvider imageProvider) {
		this.imageProvider = imageProvider;
	}

	public Map<String, String> getImageInfo(String source) throws IOException {

		Map<String, String> imageInfo = new HashMap<>();
		BufferedImage image = imageProvider.readImage(source);

		imageInfo.put(IImageConstant.IMAGE_WIDTH,
				String.valueOf(image.getWidth()));
		imageInfo.put(IImageConstant.IMAGE_HEIGHT,
				String.valueOf(image.getHeight()));
		imageInfo.put(IImageConstant.IMAGE_PATH, source);
		imageInfo.put(IImageConstant.IMAGE_QUALITY, "");
		imageInfo.put(IImageConstant.IMAGE_SIZE, "");
		imageInfo.put(IImageConstant.IMAGE_SUFFIX, "");
		return imageInfo;
	}

	public void resizeImage(String source, String target, int width, int height)
			throws IOException {

		BufferedImage sourceImage = imageProvider.readImage(source);
		AffineTransform affineTransform = new AffineTransform();
		// 缩放操作到指定宽高
		Map<String, String> imageInfo = this.getImageInfo(source);
		Dimension dim = new Dimension(Integer.parseInt(imageInfo
				.get(IImageConstant.IMAGE_WIDTH)), Integer.parseInt(imageInfo
				.get(IImageConstant.IMAGE_HEIGHT)));
		Dimension targetSize = ImageStrategy.getTargetSizeByHeight(dim, height);
		affineTransform.scale(targetSize.getWidth() / sourceImage.getWidth(),
				targetSize.getHeight() / sourceImage.getHeight());

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_DEFAULT);
		// RenderingHints hints = new
		// RenderingHints(RenderingHints.KEY_RENDERING,
		// RenderingHints.VALUE_RENDER_QUALITY);
		AffineTransformOp affineTransformOp = new AffineTransformOp(
				affineTransform, hints);

		// BufferedImage image = new BufferedImage((int)targetSize.getWidth(),
		// (int)targetSize.getHeight(), sourceImage.getType());
		BufferedImage image = new BufferedImage((int) targetSize.getWidth(),
				(int) targetSize.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		image = affineTransformOp.filter(sourceImage, image);
		imageProvider.saveImage(target, image);

	}

	public void clipImage(String sourceFile, String targetFile, int intx,
			int inty, int width, int height) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream is = null;
		ImageInputStream iis = null;
		// 读取图片文件
		is = new FileInputStream(sourceFile);
		String extensionName = this.getExtensionName(sourceFile);
		Iterator<ImageReader> it = ImageIO
				.getImageReadersByFormatName(extensionName);
		ImageReader reader = it.next();
		// 获取图片流
		iis = ImageIO.createImageInputStream(is);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();

		Rectangle rect = new Rectangle(intx, inty, width, height);
		param.setSourceRegion(rect);
		BufferedImage targetImage = reader.read(0, param);
		imageProvider.saveImage(targetFile, targetImage);
	}

	private String getExtensionName(String sourceFile) {
		if ((sourceFile != null) && (sourceFile.length() > 0)) {
			int dot = sourceFile.lastIndexOf('.');
			if ((dot > -1) && (dot < (sourceFile.length() - 1))) {
				return sourceFile.substring(dot + 1);
			}
		}
		return sourceFile;
	}

	public static void main(String[] args) {

		IImageProvider imageProvider = new IOImageProvider();
		IOImageHandler imageHandler = new IOImageHandler(imageProvider);
		try {
			imageHandler
					.resizeImage("d:\\desert.jpg", "d:\\aaa1.jpg", 400, 400);
			// imageHandler.clipImage("d:\\desert.jpg", "d:\\test_11125.jpg",
			// 100,100, 400,400);
			// imageHandler.clipImage11("d:\\test.bmp",
			// "d:\\test_111.jpg",100,100,250,250);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void compressionImage(String srcImage, int[] width,
			boolean isWatermark) {
		// TODO Auto-generated method stub

	}

	@Override
	public void compressionImage(String srcImage, int[] width,
			boolean isWatermark, String watermarkWord) {
		// TODO Auto-generated method stub

	}
}
