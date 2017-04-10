package com.framework.image.waterMark.impl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.framework.image.waterMark.IWaterMark;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class WaterMarker implements IWaterMark {

	public void pressImage(String pressImg, String targetImg, int x, int y)
			throws IOException {
		// 目标文件
		File _file = new File(targetImg);

		Image src = ImageIO.read(_file);

		int width = src.getWidth(null);
		int height = src.getHeight(null);

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g = (Graphics2D) image.getGraphics();
		// 在图形和图像中实现混合和透明效果
		AlphaComposite alpha = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.5f);

		g.drawImage(src, 0, 0, width, height, null);
		g.setComposite(alpha);
		// 水印文件

		File _filebiao = new File(pressImg);
		Image src_biao = ImageIO.read(_filebiao);
		int wideth_biao = src_biao.getWidth(null);
		int height_biao = src_biao.getHeight(null);
		g.drawImage(src_biao, (width - wideth_biao) / 2,
				(height - height_biao) / 2, wideth_biao, height_biao, null);
		// 水印文件结束
		g.dispose();
		FileOutputStream out = new FileOutputStream(targetImg);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();

	}

	public void pressText(String pressText, String targetImg, String fontName,
			int fontStyle, int color, int fontSize, int x, int y)
			throws IOException {
		File _file = new File(targetImg);
		Image src = ImageIO.read(_file);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		AlphaComposite alpha = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.5f);

		Graphics2D g = (Graphics2D) image.getGraphics();
		g.drawImage(src, 0, 0, width, height, null);
		g.setComposite(alpha);
		g.setColor(new Color(color));
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.drawString(pressText, x, y);
		g.dispose();

		FileOutputStream out = new FileOutputStream(targetImg);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();

	}

	public static void main(String[] args) {

		IWaterMark waterMark = new WaterMarker();
		try {
			waterMark.pressText("重新设置", "d:\\desert.jpg", "宋体", 12, 24255, 18,
					0, 0);
			// waterMark.pressImage("d:\\test.png", "d:\\desert.jpg", 15,15);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
