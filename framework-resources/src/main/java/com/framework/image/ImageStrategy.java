package com.framework.image;

import java.awt.Dimension;

public class ImageStrategy {

	// 以宽度为准
	// 以高度为准
	// 强制宽高

	/**
	 * 以宽度为基准
	 * 
	 * @param sourceSize
	 * @param targetWidth
	 * @return
	 */
	public static Dimension getTargetSizeByWidth(Dimension sourceSize,
			int targetWidth) {

		Dimension dimension = new Dimension();
		int sourceWidth = sourceSize.width;

		double ratio = (double) targetWidth / sourceWidth;

		int targetHeight = (int) (ratio * sourceSize.height);
		dimension.setSize(targetWidth, targetHeight);

		return dimension;
	}

	/**
	 * 以高度为基准
	 * 
	 * @param sourceSize
	 * @param targetHeight
	 * @return
	 */
	public static Dimension getTargetSizeByHeight(Dimension sourceSize,
			int targetHeight) {

		Dimension dimension = new Dimension();
		int sourceHeight = sourceSize.height;
		double ratio = (double) targetHeight / sourceHeight;
		int targetWidth = (int) (ratio * sourceSize.width);

		dimension.setSize(targetWidth, targetHeight);
		return dimension;
	}

	/**
	 * 固定宽高
	 * 
	 * @param sourceSize
	 * @return
	 */
	public static Dimension getTargetSizeByConstraint(Dimension sourceSize) {
		return sourceSize;
	}

	/**
	 * 根据宽高自动调整，以较长的为准
	 * 
	 * @param sourceSize
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 */
	public static Dimension getTargetSizeAuto(Dimension sourceSize,
			int targetWidth, int targetHeight) {
		int sourceWidth = sourceSize.width;
		int sourceHeight = sourceSize.height;
		if (sourceHeight < sourceWidth) {
			return getTargetSizeByWidth(sourceSize, targetWidth);
		} else {
			return getTargetSizeByHeight(sourceSize, targetHeight);
		}
	}

}
