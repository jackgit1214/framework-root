package com.framework.image.handler.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.core.ImageCommand;
import org.im4java.core.Operation;
import org.im4java.process.ArrayListOutputConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.framework.common.util.RandomGUID;
import com.framework.image.IImageConstant;
import com.framework.image.handler.AbstractImageHandler;
import com.framework.image.handler.IImageHandler;

@Component(value = "iM4ImageHandler")
public class IM4ImageHandler extends AbstractImageHandler implements
		IImageHandler {

	@Value("#{configProperties[environmentPath]}")
	public String environmentPath = "C:/Program Files/GraphicsMagick-1.3.25-Q16";;

	public void resizeImage(String source, String target, int width, int height)
			throws IOException {
		IMOperation op = new IMOperation();
		op.addImage(source);
		op.resize(width, height);
		op.addImage(target);
		this.executeIMOp(op);
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
	 * 按照边框的尺寸生成预览图片<br>
	 * 存在边框
	 * 
	 * @param srcImage
	 * @param adaptSize
	 */
	public void adaptImage(String srcImage, int adaptSize) {
		IMOperation op = new IMOperation();
		op.background("white");
		op.addRawArgs("-thumbnail", this.getImageAreaSize(adaptSize));
		op.addRawArgs("-gravity", "center");
		op.addRawArgs("-extent", this.getImageAreaSize(adaptSize));
		op.addRawArgs("-quality", IImageConstant.QUALITY);
		op.addImage(srcImage);

		op.addImage(this.getAdaptImageName(srcImage, adaptSize));
		this.executeIMOp(op);
	}

	/*
	 * 给图片加水印
	 * 
	 * @param srcPath 源图片路径
	 */
	public void addImgText(String srcPath, String targetPath) {
		// IMOperation op = new IMOperation();
		// op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8")
		// .draw("text 5,5 aaaa.com");
		//
		// op.addImage(srcPath);
		// op.addImage(targetPath);
		// GMOperation
		// IMOperation op = new IMOperation();
		GMOperation op = new GMOperation();

		String ss;
		try {
			ss = new String("A中文测");
			op.font("宋体").gravity("southeast").pointsize(108).fill("#ff0000")
					.draw("text 105 , 105 " + ss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		op.encoding("GBK");
		op.addImage(srcPath);
		op.addImage(targetPath);

		this.executeIMOp(op);
	}

	@Override
	public void compressionImage(String srcImage, int[] width,
			boolean isWatermark) throws Exception {
		this.compressionImage(srcImage, width, isWatermark, this.watermarkWord);
	}

	@Override
	public void compressionImage(String srcImage, int[] width,
			boolean isWatermark, String watermarkWord) throws Exception {

		// TODO Auto-generated method stub

		String sourceFile = srcImage;

		if (isWatermark) {
			sourceFile = this.addImgWatermark(srcImage, watermarkWord);
		}

		for (int w : width) {
			IMOperation op = new IMOperation();
			// 压缩
			op.addRawArgs("-thumbnail", this.getImageAreaSize(w));
			// 图片质量
			op.addRawArgs("-quality", "100");
			op.addImage(sourceFile);
			op.addImage(this.getAdaptImageName(srcImage, w));

			this.executeIMOp(op);
		}

	}

	/**
	 * width 水印宽度（可以于水印图片大小不同） height 水印高度（可以于水印图片大小不同） x 水印开始X坐标 y 水印开始Y坐标
	 * 
	 * @param srcImagePath
	 * @param watermarkWord
	 * @throws Exception
	 */

	private void addWatermark(IMOperation op, String srcImagePath,
			String watermarkWord) throws Exception {

		RandomGUID ram = new RandomGUID();
		String waterMarkFile = System.currentTimeMillis() + ram.toString();
		File waterM = new File(waterMarkFile);
		ImageIO.write(this.getWatermark(watermarkWord), "png", waterM);

		// 原始图片信息
		BufferedImage buffimg = ImageIO.read(new File(srcImagePath));
		int w = buffimg.getWidth();
		int h = buffimg.getHeight();

		BufferedImage buffimg1 = ImageIO.read(new File(waterMarkFile));
		int width = buffimg1.getWidth();
		int height = buffimg1.getHeight();

		// 水印图片位置
		// op.geometry(width, height, x, y);
		switch (this.watermarkFontPosition) {
		case POS_LT:
			op.geometry(w, h, IImageConstant.WATERMARKMARGIN,
					IImageConstant.WATERMARKMARGIN);
		case POS_RT:
			op.geometry(w, h, w - width - IImageConstant.WATERMARKMARGIN,
					IImageConstant.WATERMARKMARGIN);
		case POS_LB:
			op.geometry(w, h, IImageConstant.WATERMARKMARGIN, h - height
					- IImageConstant.WATERMARKMARGIN);
		case POS_RB:
			op.geometry(width, height, w - width
					- IImageConstant.WATERMARKMARGIN, h - height
					- IImageConstant.WATERMARKMARGIN);
		case POS_ALL:

		}
		// 水印透明度
		op.dissolve(this.watermarkDissolve);
		// 生成水印 图
		op.addImage(waterMarkFile);

	}

	private void executeIMOp(Operation op) {

		ConvertCmd convert = new ConvertCmd(true);
		// convert.run(arg0, arg1);
		// linux下不要设置此值，不然会报错
		convert.setSearchPath(environmentPath);
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

	/** * 获取 ImageCommand * * @param command 命令类型 * @return */
	private ImageCommand getImageCommand(CommandType command) {
		ImageCommand cmd = null;
		switch (command) {
		case convert:
			cmd = new ConvertCmd(true);
			break;
		case identify:
			cmd = new IdentifyCmd(true);
			break;
		case compositecmd:
			cmd = new CompositeCmd(true);
			break;
		}
		if (cmd != null
				&& System.getProperty("os.name").toLowerCase()
						.indexOf("windows") == -1) {
			cmd.setSearchPath(this.environmentPath);
		}
		return cmd;
	}

	/** * 命令类型 * */
	private enum CommandType {
		convert("转换处理"), identify("图片信息"), compositecmd("图片合成");
		private String name;

		CommandType(String name) {
			this.name = name;
		}
	}

	/**
	 * * 图片水印 * * @param srcImagePath 源图片路径 * @param destImagePath 目标图片路径 * @param
	 * dissolve 透明度（0-100） * @throws Exception
	 */

	/**
	 * 
	 * @param srcImagePath
	 * @param watermarkWord
	 * @throws Exception
	 * 
	 *             width 水印宽度（可以于水印图片大小不同） height 水印高度（可以于水印图片大小不同） x 水印开始X坐标 y
	 *             水印开始Y坐标
	 * 
	 */
	public String addImgWatermark(String srcImagePath, String watermarkWord)
			throws Exception {
		RandomGUID ram = new RandomGUID();
		String waterMarkFile = System.currentTimeMillis() + ram.toString();
		File waterM = new File(waterMarkFile);
		ImageIO.write(this.getWatermark(watermarkWord), "png", waterM);
		// 原始图片信息
		BufferedImage buffimg = ImageIO.read(new File(srcImagePath));
		int w = buffimg.getWidth();
		int h = buffimg.getHeight();

		BufferedImage buffimg1 = ImageIO.read(new File(waterMarkFile));
		int width = buffimg1.getWidth();
		int height = buffimg1.getHeight();
		IMOperation op = new IMOperation();

		// 水印图片位置
		// op.geometry(width, height, x, y);
		switch (this.watermarkFontPosition) {
		case POS_LT:
			op.geometry(w, h, IImageConstant.WATERMARKMARGIN,
					IImageConstant.WATERMARKMARGIN);
		case POS_RT:
			op.geometry(w, h, w - width - IImageConstant.WATERMARKMARGIN,
					IImageConstant.WATERMARKMARGIN);
		case POS_LB:
			op.geometry(w, h, IImageConstant.WATERMARKMARGIN, h - height
					- IImageConstant.WATERMARKMARGIN);
		case POS_RB:
			op.geometry(width, height, w - width
					- IImageConstant.WATERMARKMARGIN, h - height
					- IImageConstant.WATERMARKMARGIN);

			// op.geometry(width, height, 50, 50);
		case POS_ALL:

		}
		// 水印透明度
		op.dissolve(this.watermarkDissolve);

		// 水印 图
		op.addImage(waterMarkFile);
		// 原图
		op.addImage(srcImagePath);
		// 目标

		String targetFile = this.getWatermarkImageName(srcImagePath);
		op.addImage(targetFile);
		ImageCommand cmd = getImageCommand(CommandType.compositecmd);

		cmd.run(op);
		if (waterM.exists())
			log.debug(waterM.delete());
		return targetFile;
	}

	/**
	 * * 查询图片的基本信息:格式,质量，宽度，高度 *
	 * <p>
	 * * gm identify -format %w,%h,%d/%f,%Q,%b,%e
	 * 
	 * 
	 * %b file size of image read in <br>
	 * 
	 * %c comment property <br>
	 * 
	 * %d directory component of path <br>
	 * %e filename extension or suffix <br>
	 * %f filename (including suffix) <br>
	 * %g layer canvas page geometry ( = %Wx%H%X%Y ) <br>
	 * %h current image height in pixels %i image filename (note: becomes output
	 * filename for "info:") <br>
	 * %k number of unique colors <br>
	 * %l label property <br>
	 * %m image file format (file magic) <br>
	 * %n exact number of images in current image sequence <br>
	 * %o output filename (used for delegates) <br>
	 * %p index of image in current image list <br>
	 * %q quantum depth (compile-time constant) <br>
	 * %r image class and colorspace <br>
	 * %s scene number (from input unless re-assigned) <br>
	 * %t filename without directory or extension (suffix) <br>
	 * %u unique temporary filename (used for delegates) <br>
	 * %w current width in pixels <br>
	 * %x x resolution (density) <br>
	 * %y y resolution (density) <br>
	 * %z image depth (as read in unless modified, image save depth) <br>
	 * %A image transparency channel enabled (true/false) <br>
	 * %C image compression type %D image dispose method %G image size ( = %wx%h
	 * ) <br>
	 * %H page (canvas) height <br>
	 * %M Magick filename (original file exactly as given, including read mods)
	 * <br>
	 * %O page (canvas) offset ( = %X%Y ) <br>
	 * %P page (canvas) size ( = %Wx%H ) <br>
	 * %Q image compression quality ( 0 = default ) <br>
	 * %S ?? scenes ?? <br>
	 * %T image time delay <br>
	 * %W page (canvas) width <br>
	 * %X page (canvas) x offset (including sign) %Y page (canvas) y offset
	 * (including sign) <br>
	 * %Z unique filename (used for delegates) <br>
	 * %@ bounding box <br>
	 * %# signature <br>
	 * %% a percent sign \n newline \r carriage return
	 * 
	 * <p>
	 * * * @param imagePath * @return
	 */
	public Map<String, String> getImageInfo(String imagePath) {

		long startTime = System.currentTimeMillis();
		Map<String, String> imageInfo = new HashMap<>();
		try {
			IMOperation op = new IMOperation();
			op.format("%w,%h,%d/%f,%Q,%b,%e");
			op.addImage();
			ImageCommand identifyCmd = getImageCommand(CommandType.identify);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();
			String[] result = cmdOutput.get(0).split(",");
			if (result.length == 6) {
				imageInfo.put(IImageConstant.IMAGE_WIDTH, result[0]);
				imageInfo.put(IImageConstant.IMAGE_HEIGHT, result[1]);
				imageInfo.put(IImageConstant.IMAGE_PATH, result[2]);
				imageInfo.put(IImageConstant.IMAGE_QUALITY, result[3]);
				imageInfo.put(IImageConstant.IMAGE_SIZE, result[4]);
				imageInfo.put(IImageConstant.IMAGE_SUFFIX, result[5]);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("图片工具获取图片基本信息异常" + e.getMessage(), e);
		}
		long endTime = System.currentTimeMillis();
		log.info("take time: " + (endTime - startTime));
		return imageInfo;
	}

	/**
	 * * 图片旋转(顺时针旋转) * 拼装命令示例: gm convert -rotate 90 /apps/watch.jpg
	 * /apps/watch_compress.jpg * * @param imagePath 源图片路径 * @param newPath
	 * 处理后图片路径 * @param degree 旋转角度
	 */
	public static boolean rotate(String imagePath, String newPath, double degree) {
		boolean flag;
		try {
			// 1.将角度转换到0-360度之间
			degree = degree % 360;
			if (degree <= 0) {
				degree = 360 + degree;
			}
			IMOperation op = new IMOperation();
			op.rotate(degree);
			op.addImage(imagePath);
			op.addImage(newPath);
			ConvertCmd cmd = new ConvertCmd(true);
			cmd.run(op);
			flag = true;
		} catch (Exception e) {
			flag = false;
			System.out.println("图片旋转失败!");
		}
		return flag;
	}

	public static void main(String[] args) {

		IM4ImageHandler imageHandler = new IM4ImageHandler();
		try {
			// imageHandler.orgImage("d:\\1.jpg", true);
			// imageHandler.compressionImage("d:\\1.jpg", 50);
			// imageHandler.addImgText("d:\\1.jpg", "d:\\2.jpg");
			// imageHandler.addImgWatermark("d:\\1.jpg", "重新测试！www.sohu.com");
			int[] width = { 800, 600, 400 };
			imageHandler.compressionImage("d:\\1.jpg", width, true,
					"www.sohu.com");
			// imageHandler.getImageInfo("d:\\1.jpg");
			// imageHandler.adaptImage("d:\\1.jpg", 800);
			// imageHandler.orgImage("d:\\1.jpg", "d:\\4.jpg", true);
			// imageHandler.resizeImage("d:\\1.jpg", "d:\\5.jpg", 100, 100);
			// imageHandler.compressionImage("d:\\1.jpg", 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
