package com.framework.image.handler.impl;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import magick.ImageInfo;
import magick.MagickApiException;
import magick.MagickException;
import magick.MagickImage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.framework.image.IImageConstant;
import com.framework.image.handler.IImageHandler;

@Component(value = "iM4ImageHandler")
public class IM4ImageHandler implements IImageHandler {

	protected final Log log = LogFactory.getLog(this.getClass());

	// # 1为linux，2为windows
	@Value("#{configProperties[environment]}")
	public String environment;

	@Value("#{configProperties[environmentPath]}")
	public String environmentPath;

	public static String imageMagickPath = null;
	static {
		imageMagickPath = "C:/Program Files (x86)/ImageMagick-6.3.9-Q16";
	}

	public Dimension getSize(String source) throws IOException {
		try {
			ImageInfo info = new ImageInfo(source);
			MagickImage image = new MagickImage(info);
			return image.getDimension();
		} catch (MagickApiException ex) {
			throw new IOException(ex.getMessage());
		} catch (MagickException ex) {
			throw new IOException(ex.getMessage());
		}
	}

	public void resizeImage(String source, String target, int width, int height)
			throws IOException {
		IMOperation op = new IMOperation();
		op.addImage(source);
		op.resize(width, height);
		op.addImage(target);
		this.executeIMOp(op);
	}

	private void executeIMOp(IMOperation op) {
		ConvertCmd convert = new ConvertCmd();
		// linux下不要设置此值，不然会报错
		convert.setSearchPath(imageMagickPath);
		try {
			convert.run(op);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IM4JavaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clipImage(String source, String target, int intx, int inty,
			int width, int height) throws IOException {
		IMOperation op = new IMOperation();
		op.addImage(source);
		/**
		 * width： 裁剪的宽度 height： 裁剪的高度 x： 裁剪的横坐标 y： 裁剪的挫坐标
		 */
		op.crop(width, height, intx, inty);
		op.addImage(target);
		this.executeIMOp(op);

	}

	/**
	 * 按照边框的尺寸生成预览图片
	 * 
	 * @param srcImage
	 * @param adaptSize
	 */
	private void adaptImage(String srcImage, int adaptSize) {
		IMOperation op = new IMOperation();
		op.background("white");
		op.addRawArgs("-thumbnail", this.getImageAreaSize(adaptSize));
		op.addRawArgs("-gravity", "center");
		op.addRawArgs("-extent", this.getImageAreaSize(adaptSize));
		op.addRawArgs("-quality", IImageConstant.ADAPT);
		op.addImage(srcImage);
		op.addImage(this.getAdaptImageName(srcImage, adaptSize));
		this.executeIMOp(op);
	}

	/**
	 * <p>
	 * 对很大的图片进行压缩,
	 * </p>
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	private String getScaling(int width) {
		double percent = (IImageConstant.MAX_DETAIL / width) * 100;
		return percent + "%x" + percent + "%";
	}

	/**
	 * 生成默认的图片格式
	 * 
	 * @param srcImage
	 * @param watermark
	 */
	public void orgImage(String srcImage, String targetImage, boolean watermark) {
		IMOperation op = new IMOperation();
		if (watermark) {
			op.font("宋体").gravity("southeast").pointsize(24).fill("#BCBFC8")
					.draw("text 5,5 xx.com");
		}
		op.background("white");
		// op.addRawArgs("-thumbnail", this.getImageAreaSize(srcImage));
		op.addRawArgs("-gravity", "center");
		op.addRawArgs("-extent", this.getImageAreaSize(srcImage));
		// op.addRawArgs("-quality", IImageConstant.QUALITY_ORG);
		op.addImage(srcImage);
		op.addImage(targetImage);

		this.executeIMOp(op);
	}

	/*
	 * 给图片加水印
	 * 
	 * @param srcPath 源图片路径
	 */
	public void addImgText(String srcPath, String targetPath) {
		IMOperation op = new IMOperation();
		op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8")
				.draw("text 5,5 juziku.com");

		op.addImage(srcPath);
		op.addImage(targetPath);
		this.executeIMOp(op);
	}

	/**
	 * <p>
	 * 获取最大的图片边框，业务规则是 ：<Br>
	 * 1、如果长比宽大，并少于预设边框800的时候则选择长为压缩的边框做标准;如果大于预设边框的时候则选择800做为标准<br>
	 * 2、如果宽比长大,并少于预设边框800的时候则选择宽为压缩的边框做标准;如果大于预设边框的时候则选择800做为标准<br>
	 * </p>
	 * 
	 * @param srcImage
	 * @return
	 * @throws IOException
	 */
	private String getImageAreaSize(String srcImage) {
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(srcImage));
			int height = bufferedImage.getHeight();
			int width = bufferedImage.getWidth();
			int tempMaxSelectSize = (height > width ? height : width);
			int tempMaxSize = (tempMaxSelectSize > IImageConstant.MAX_SIZE ? IImageConstant.MAX_SIZE
					: tempMaxSelectSize);
			return tempMaxSize + "x" + tempMaxSize;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return "1x1";
	}

	/**
	 * 获取最后的自适应图片地址，不建议这么做，毕竟这样会影响到.gif的图片。
	 * 
	 * @param srcImage
	 * @param adaptSize
	 * @return
	 */
	private String getAdaptImageName(String srcImage, int adaptSize) {
		return new StringBuffer(srcImage).append("_").append(adaptSize)
				.append("x").append(adaptSize).append(".jpg").toString();
	}

	/**
	 * 获取自适应图片的规格
	 * 
	 * @param adaptSize
	 * @return
	 */
	private String getImageAreaSize(int adaptSize) {
		return adaptSize + "x" + adaptSize;
	}

	@Override
	public void compressionImage(String srcImage, int width) {
		// TODO Auto-generated method stub
		IMOperation op = new IMOperation();
		// 压缩
		op.addRawArgs("-thumbnail", "100x100");
		// 图片质量
		op.addRawArgs("-quality", "100");
		if (width > IImageConstant.MAX_DETAIL) {
			// op.addImage("-sample", getScaling(width));
		}
		op.addImage(srcImage);
		op.addImage(this.getAdaptImageName(srcImage, 100));
		this.executeIMOp(op);
	}

	public static void main(String[] args) {

		IM4ImageHandler imageHandler = new IM4ImageHandler();
		try {
			// imageHandler.orgImage("d:\\1.jpg", true);
			// imageHandler.compressionImage("d:\\1.jpg", 50);
			imageHandler.addImgText("d:\\1.jpg", "d:\\2.jpg");
			imageHandler.orgImage("d:\\1.jpg", "d:\\4.jpg", true);
			imageHandler.resizeImage("d:\\1.jpg", "d:\\5.jpg", 100, 100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
