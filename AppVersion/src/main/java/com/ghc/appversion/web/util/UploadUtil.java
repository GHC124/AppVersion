package com.ghc.appversion.web.util;

import static com.ghc.appversion.web.Constants.ANDROID_TYPE;
import static com.ghc.appversion.web.Constants.PHOTO_TYPE;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;

import com.ghc.appversion.util.LogUtil;

public class UploadUtil {
	public static boolean isValidPhoto(String type) {
		boolean valid = false;
		if (PHOTO_TYPE.contains(type)) {
			valid = true;
		}
		return valid;
	}

	public static boolean isValidAndroid(String originalName) {
		LogUtil.error("Name %s", originalName);
		boolean valid = false;
		int index = originalName.lastIndexOf(".");
		if (index != -1) {
			String type = originalName.substring(index);
			LogUtil.error("Type %s", type);
			if (ANDROID_TYPE.contains(type)) {
				valid = true;
			}
		}
		return valid;
	}

	/**
	 * Create upload folder
	 * 
	 * @throws IOException
	 */
	public static void createUploadFolder(String rootDirectory)
			throws IOException {
		Path iconPath = Paths.get(rootDirectory + "\\AppIcon");
		Path binaryPath = Paths.get(rootDirectory + "\\AppBinary");

		if (!Files.exists(iconPath)) {
			Files.createDirectories(iconPath);
		}
		if (!Files.exists(binaryPath)) {
			Files.createDirectories(binaryPath);
		}
	}

	/**
	 * Save icon file
	 * 
	 * @return Icon local url
	 * @throws IOException
	 */
	public static String saveIconFile(String rootDirectory,
			InputStream inputStream) throws IOException {
		String fileName = String.format("icon_%s.png", Calendar.getInstance()
				.getTimeInMillis());
		String filePath = String.format("%s\\AppIcon\\%s", rootDirectory,
				fileName);
		OutputStream outputStream = new FileOutputStream(filePath);
		IOUtils.copy(inputStream, outputStream);
		outputStream.flush();
		outputStream.close();
		inputStream.close();
		String iconUrl = String.format("\\AppIcon\\%s", fileName);
		return iconUrl;
	}

	/**
	 * Save android file
	 * 
	 * @return Icon local url
	 * @throws IOException
	 */
	public static String saveAndroidFile(String rootDirectory,
			InputStream inputStream) throws IOException {
		String fileName = String.format("android_%s.apk", Calendar
				.getInstance().getTimeInMillis());
		String filePath = String.format("%s\\AppBinary\\%s", rootDirectory,
				fileName);
		OutputStream outputStream = new FileOutputStream(filePath);
		IOUtils.copy(inputStream, outputStream);		
		outputStream.flush();
		outputStream.close();
		inputStream.close();
		String iconUrl = String.format("\\AppBinary\\%s", fileName);
		return iconUrl;
	}

	public static byte[] getIconFile(String rootDirectory, String iconUrl) throws IOException {
		String filePath = String.format("%s%s", rootDirectory, iconUrl);
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			InputStream inputStream = new FileInputStream(filePath);
			return IOUtils.toByteArray(inputStream);
		}
		
		return null;
	}
	
	public static void deleteIconFile(String rootDirectory, String iconUrl)
			throws IOException {
		String filePath = String.format("%s%s", rootDirectory, iconUrl);
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			Files.delete(path);
		}
	}
	
	public static void deleteAndroidFile(String rootDirectory, String appUrl)
			throws IOException {
		String filePath = String.format("%s%s", rootDirectory, appUrl);
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			Files.delete(path);
		}
	}
}
