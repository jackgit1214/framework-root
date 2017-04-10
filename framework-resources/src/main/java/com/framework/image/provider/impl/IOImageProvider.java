package com.framework.image.provider.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import com.framework.image.provider.IImageProvider;

public class IOImageProvider implements IImageProvider {

	public void saveImage(String targerFileName, BufferedImage bufferimage)
			throws IOException {
		File targetFile = new File(targerFileName);
		ImageWriter writer = null;
		ImageOutputStream outputStream = null;
		try {
			ImageTypeSpecifier type = ImageTypeSpecifier
					.createFromRenderedImage(bufferimage);
			Iterator iterator = ImageIO.getImageWriters(type, "JPEG");
			if (!iterator.hasNext()) {
				return;
			}
			writer = (ImageWriter) iterator.next();
			IIOImage iioImage = new IIOImage(bufferimage, null, null);
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(1.0f);
			outputStream = ImageIO.createImageOutputStream(targetFile);
			writer.setOutput(outputStream);
			writer.write(null, iioImage, param);
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (writer != null) {
				writer.abort();
			}
		}

	}

	public BufferedImage readImage(String sourceFilename) throws IOException {
		return ImageIO.read(new File(sourceFilename));
	}

}
