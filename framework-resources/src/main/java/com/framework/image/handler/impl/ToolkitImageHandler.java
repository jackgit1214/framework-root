package com.framework.image.handler.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.framework.image.IImageConstant;
import com.framework.image.ImageStrategy;
import com.framework.image.handler.IImageHandler;
import com.framework.image.provider.IImageProvider;
import com.framework.image.provider.impl.IOImageProvider;

public class ToolkitImageHandler implements IImageHandler {

	private IImageProvider imageProvider;

	public ToolkitImageHandler(IImageProvider imageProvider) {
		super();
		this.imageProvider = imageProvider;
	}

	public Map<String, String> getImageInfo(String source) throws IOException {
		Image image = Toolkit.getDefaultToolkit().getImage(source);

		Map<String, String> imageInfo = new HashMap<>();

		imageInfo.put(IImageConstant.IMAGE_WIDTH,
				String.valueOf(image.getWidth(null)));
		imageInfo.put(IImageConstant.IMAGE_HEIGHT,
				String.valueOf(image.getHeight(null)));
		imageInfo.put(IImageConstant.IMAGE_PATH, source);
		imageInfo.put(IImageConstant.IMAGE_QUALITY, "");
		imageInfo.put(IImageConstant.IMAGE_SIZE, "");
		imageInfo.put(IImageConstant.IMAGE_SUFFIX, "");
		return imageInfo;

	}

	public void resizeImage(String sourceFile, String targetFile, int width,
			int height) throws IOException {

		BufferedImage sourceImage = imageProvider.readImage(sourceFile);
		Map<String, String> imageInfo = this.getImageInfo(sourceFile);
		Dimension dim = new Dimension(Integer.parseInt(imageInfo
				.get(IImageConstant.IMAGE_WIDTH)), Integer.parseInt(imageInfo
				.get(IImageConstant.IMAGE_HEIGHT)));
		Dimension targetSize = ImageStrategy.getTargetSizeByHeight(dim, height);

		BufferedImage image = new BufferedImage(targetSize.width,
				targetSize.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = null;
		try {
			g = (Graphics2D) image.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);

			g.setColor(Color.white);
			g.fillRect(0, 0, targetSize.width, targetSize.height);
			g.drawImage(sourceImage, 0, 0, targetSize.width, targetSize.height,
					null);
		} finally {
			if (g != null) {
				g.dispose();
			}
		}
		imageProvider.saveImage(targetFile, image);

	}

	public void clipImage(String sourceFile, String targetFile, int intx,
			int inty, int width, int height) throws IOException {
		Image image;
		ImageFilter cropFilter;

		// 读取源图像
		BufferedImage bufferImage = this.imageProvider.readImage(sourceFile);
		cropFilter = new CropImageFilter(intx, inty, width, height);

		image = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(bufferImage.getSource(), cropFilter));

		BufferedImage tagetImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics g = tagetImage.getGraphics();

		g.drawImage(image, 0, 0, null); // 绘制小图
		g.dispose();

		imageProvider.saveImage(targetFile, tagetImage);
	}

	public static void main(String[] args) {

		IImageProvider imageProvider = new IOImageProvider();
		IImageHandler imageHandler = new ToolkitImageHandler(imageProvider);
		try {
			imageHandler.clipImage("d:\\desert.jpg", "d:\\test_111.jpg", 100,
					100, 400, 400);
			imageHandler.resizeImage("d:\\desert.jpg", "d:\\test_1112.jpg",
					200, 200);
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
