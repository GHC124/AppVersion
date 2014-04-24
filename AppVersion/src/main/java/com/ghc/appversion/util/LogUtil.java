/**
 * Log.java
 *
 *	
 */
package com.ghc.appversion.util;

import org.apache.log4j.Logger;

/**
 * 
 */
public class LogUtil {
	private static boolean mEnableLog = true;
	private static final Logger sLog = Logger.getLogger(LogUtil.class);

	private LogUtil() {

	}

	public static void setEnableLog(boolean enableLog) {
		mEnableLog = enableLog;
	}

	public static void debug(String message) {
		debug(message, "");
	}

	public static void debug(String message, Object... args) {
		if (mEnableLog) {
			String format = String.format(message, args);
			sLog.debug(format);
		}
	}
	
	public static void error(String message) {
		error(message, "");
	}

	public static void error(String message, Object... args) {
		if (mEnableLog) {
			String format = String.format(message, args);
			sLog.error(format);
		}
	}
	
	public static void info(String message) {
		info(message, "");
	}

	public static void info(String message, Object... args) {
		if (mEnableLog) {
			String format = String.format(message, args);
			sLog.info(format);
		}
	}
}
