package com.framework.image.handler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import com.framework.image.IImageConstant;

public abstract class AbstractImageHandler {

	protected final Log log = LogFactory.getLog(this.getClass());

	// # 水印字体类型，默认为宋体
	@Value("#{configProperties[watermarkFont]}")
	protected String watermarkFont = "宋体";

	// # 水印字体大小,默认为48
	@Value("#{configProperties[watermarkFontSize]}")
	protected int watermarkFontSize = 48;

	// # 水印字体颜色，默认为红色
	@Value("#{configProperties[watermarkFontColor]}")
	protected String watermarkFontColor = "f0d276";

	// # 水印字体颜色，默认为红色
	@Value("#{configProperties[watermarkWord]}")
	protected String watermarkWord = "www.XXXX.com";

	// # 水印位置 ，
	// 几种方式，左上，右上，左下，右下，平铺
	@Value("#{configProperties[watermarkFontPosition]}")
	protected int position = 3;

	protected IImageConstant.WartermarkPosition watermarkFontPosition = IImageConstant.WartermarkPosition
			.valueOf(position);

	@Value("#{configProperties[watermarkDissolve]}")
	protected int watermarkDissolve = IImageConstant.WATERMARKDISSOLVE;

	/**
	 * 获取最后的自适应图片地址，不建议这么做，毕竟这样会影响到.gif的图片。
	 * 
	 * @param srcImage
	 * @param adaptSize
	 * @return
	 */
	protected String getAdaptImageName(String srcImage, int adaptSize) {

		String tmpImage = srcImage.substring(0, srcImage.lastIndexOf("."));
		return new StringBuffer(tmpImage).append("_").append(adaptSize)
				.append("x").append(adaptSize).append(".jpg").toString();
	}

	protected String getWatermarkImageName(String srcImage) {

		String tmpImage = srcImage.substring(0, srcImage.lastIndexOf("."));
		return new StringBuffer(tmpImage).append("_").append("watermark")
				.append(".jpg").toString();
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
	protected String getScaling(int width) {
		double percent = (IImageConstant.MAX_DETAIL / width) * 100;
		return percent + "%x" + percent + "%";
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
	protected String getImageAreaSize(String srcImage) {
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
	 * 获取自适应图片的规格
	 * 
	 * @param adaptSize
	 * @return
	 */
	protected String getImageAreaSize(int adaptSize) {
		return adaptSize + "x" + adaptSize;
	}

	/**
	 * 生成水印文字图片，针对im4水印中文乱码问题，单独生成图片后合成
	 * 
	 * @param waterMarkWord
	 *            水印文字信息
	 * @return
	 * @author lilj
	 * @throws IOException
	 */
	protected BufferedImage getWatermark(String watermarkWord)
			throws IOException {

		int imageHeight = 100;// 图片的高度

		// 根据文字计算水印文件宽度
		Font font = new Font(this.watermarkFont, Font.BOLD,
				this.watermarkFontSize);
		FontMetrics fm = new JLabel().getFontMetrics(font);
		int textWidth = fm.stringWidth(watermarkWord);
		imageHeight = fm.getHeight();

		BufferedImage image = new BufferedImage(textWidth, imageHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		// g2d.setBackground(new Color(0, 220, 123));
		image = g2d.getDeviceConfiguration().createCompatibleImage(textWidth,
				imageHeight, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();

		Color color = new Color(Integer.parseInt(this.watermarkFontColor, 16));

		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(1));

		g2d.setFont(font);
		g2d.drawString(watermarkWord, 0, fm.getAscent());

		// ByteArrayOutputStream out = new ByteArrayOutputStream();

		// ImageIO.write(image, "png", new File("d://2.png"));
		// ImageIO.write(image, "png", out);

		// return out.toByteArray();
		return image;
	}

	protected int[] getXY(int[] image, int[] watermark, int position, int x,
			int y) {
		int[] xy = new int[2];
		if (position == 1) {
			xy[0] = x;
			xy[1] = y;
		} else if (position == 2) {
			xy[0] = (image[0] - watermark[0]) / 2; // 横向边距
			xy[1] = y; // 纵向边距
		} else if (position == 3) {
			xy[0] = image[0] - watermark[0] - x;
			xy[1] = y;
		} else if (position == 4) {
			xy[0] = x;
			xy[1] = (image[1] - watermark[1]) / 2;
		} else if (position == 5) {
			xy[0] = (image[0] - watermark[0]) / 2;
			xy[1] = (image[1] - watermark[1]) / 2;
		} else if (position == 6) {
			xy[0] = image[0] - watermark[0] - x;
			xy[1] = (image[1] - watermark[1]) / 2;
		} else if (position == 7) {
			xy[0] = x;
			xy[1] = image[1] - watermark[1] - y;
		} else if (position == 8) {
			xy[0] = (image[0] - watermark[0]) / 2;
			xy[1] = image[1] - watermark[1] - y;
		} else {
			xy[0] = image[0] - watermark[0] - x;
			xy[1] = image[1] - watermark[1] - y;
		}
		return xy;
	}
}
