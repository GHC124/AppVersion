/**
 * ErrorMessage.java
 *
 *	
 */
package com.ghc.appversion.web.form;

/**
 * 
 */
public class ErrorMessage {
	private String mFieldName;
	private String mMessage;

	public ErrorMessage(String fieldName, String message) {
		super();
		mFieldName = fieldName;
		mMessage = message;
	}

	public String getFieldName() {
		return mFieldName;
	}

	public void setFieldName(String fieldName) {
		mFieldName = fieldName;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String message) {
		mMessage = message;
	}

}
