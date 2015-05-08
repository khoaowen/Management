package com.khoaowen.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;


public class ImageUtil {

	public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth,
			int targetHeight, Object hint, boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}
	
	/**
	 * 
	 * @param path path to file located in resource folder
	 * @param width
	 * @param height
	 * @return
	 */
	public static Image getImageResources(String path, int width, int height) {
		Image img = new Image(ImageUtil.class.getResourceAsStream(path), width, height, true, true);
		return img;
	}
	
	/**
	 * 
	 * @param path path to file located in resource folder
	 * @return
	 */
	public static Image getImageResources(String path) {
		Image img = new Image(ImageUtil.class.getResourceAsStream(path));
		return img;
	}
	
	/**
	 * Resize the image file to width of 400 by keeping the ratio height/width
	 * @param imagePath
	 * @return
	 */
	public static byte[] convertToByte(String imagePath) {
		File file = new File(imagePath); 
		BufferedImage img = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream((int)file.length());
		try {
			img = ImageIO.read(file);
			int newWidth = 400;
			BufferedImage scaledBuff = getScaledInstance(img, newWidth, calculateHeightFromWidth(img.getHeight(), img.getWidth(), newWidth), RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, true);
			ImageIO.write(scaledBuff,FilenameUtils.getExtension(file.getName()), output);
		} catch (NullPointerException e) {
			ExceptionHandler.showMessage(ResourceBundlesHelper.getMessageBundles("not.imageFile.warning.text", file.getName()), AlertType.WARNING);
		}catch (Exception e) {
			ExceptionHandler.showErrorAndLog(e);
		}
		return output.toByteArray();
	}

	private static int calculateHeightFromWidth(int orginalHeight, int originalWidth, int newWidth) {
		double height = (double) orginalHeight / (double) originalWidth * (double) newWidth;
		return (int) height;
	}
}
