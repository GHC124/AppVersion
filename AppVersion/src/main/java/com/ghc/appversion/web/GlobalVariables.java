package com.ghc.appversion.web;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * Global Variables for entire application
 *
 */
public class GlobalVariables {
	private static GlobalVariables INSTANCE;

	private String mUploadRootDirectory;
	private String mDateFormatPattern;
	private String mPhotoType;
	private String mAndroidType;
	private String mIOSType;

	private GlobalVariables() {
		mUploadRootDirectory = "";
		mDateFormatPattern = "";
		mPhotoType = "";
		mAndroidType = "";
		mIOSType = "";
	}

	/**
	 * Init data
	 */
	public static void init(MessageSource messageSource) {
		GlobalVariables globalVariables = getInstance();

		// Load global variables
		globalVariables.mUploadRootDirectory = messageSource.getMessage(
				"file_upload_directory", new Object[] {}, Locale.US);
		globalVariables.mDateFormatPattern = messageSource.getMessage(
				"date_format_pattern", new Object[] {}, Locale.US);
		globalVariables.mPhotoType = messageSource.getMessage("photo_type",
				new Object[] {}, Locale.US);
		globalVariables.mAndroidType = messageSource.getMessage("android_type",
				new Object[] {}, Locale.US);
		globalVariables.mIOSType = messageSource.getMessage("ios_type",
				new Object[] {}, Locale.US);
	}

	public static GlobalVariables getInstance() {
		if (INSTANCE == null) {
			synchronized (GlobalVariables.class) {
				if (INSTANCE == null) {
					INSTANCE = new GlobalVariables();
				}
			}
		}
		return INSTANCE;
	}

	public String getUploadRootDirectory() {
		return mUploadRootDirectory;
	}

	public String getDateFormatPattern() {
		return mDateFormatPattern;
	}

	public String getPhotoType() {
		return mPhotoType;
	}

	public String getAndroidType() {
		return mAndroidType;
	}

	public String getIOSType() {
		return mIOSType;
	}

}
