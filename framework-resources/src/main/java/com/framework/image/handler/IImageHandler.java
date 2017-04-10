package com.framework.image.handler;

import java.awt.Dimension;
import java.io.IOException;

public interface IImageHandler {

	/**
	 * 取图片的原始尺寸
	 * 
	 * @param source
	 * @throws IOException
	 */
	public Dimension getSize(String source) throws IOException;

	/**
	 * 根据宽度，高度缩放生成图片
	 * 
	 * @param source
	 * @param target
	 * @param width
	 *            生成图片的宽度
	 * @param height
	 *            高度
	 * @throws IOException
	 */
	public void resizeImage(String source, String target, int width, int height)
			throws IOException;

	/**
	 * 根据给定的x,y值，对图片进行剪切
	 * 
	 * @param source
	 * @param target
	 * @param intx
	 * @param inty
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public void clipImage(String source, String target, int intx, int inty,
			int width, int height) throws IOException;

	/**
	 * <p>
	 * 对图片进行压缩,主要使用场景是上传图片对图片进行压缩，生成缩略图
	 * </p>
	 * 
	 * @param srcImage
	 */
	public void compressionImage(String srcImage, int width);

}
