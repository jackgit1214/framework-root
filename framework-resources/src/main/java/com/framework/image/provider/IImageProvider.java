package com.framework.image.provider;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface IImageProvider {

	public void saveImage(String targerFileName, BufferedImage bufferimage)
			throws IOException;

	public BufferedImage readImage(String sourceFilename) throws IOException;

}
