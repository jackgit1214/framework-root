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

	public Dimension getSize(String source) throws IOException {
		Image image = Toolkit.getDefaultToolkit().getImage(source);

		return new Dimension(image.getWidth(null), image.getHeight(null));
	}

	public void resizeImage(String sourceFile, String targetFile, int width,
			int height) throws IOException {

		BufferedImage sourceImage = imageProvider.readImage(sourceFile);
		Dimension test = this.getSize(sourceFile);
		Dimension targetSize = ImageStrategy
				.getTargetSizeByHeight(test, height);

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
	public void compressionImage(String srcImage, int width) {
		// TODO Auto-generated method stub

	}
}
