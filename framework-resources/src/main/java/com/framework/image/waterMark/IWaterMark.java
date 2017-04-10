package com.framework.image.waterMark;

import java.io.IOException;

public interface IWaterMark {

	public void pressImage(String pressImg, String targetImg, int x, int y)
			throws IOException;

	public void pressText(String pressText, String targetImg, String fontName,
			int fontStyle, int color, int fontSize, int x, int y)
			throws IOException;
}
