/**
 * MessageResponse.java
 *
 *	
 */
package com.ghc.appversion.web.rest;

/**
 * 
 */
public class MessageResponse {
	public static final String FAIL = "FAIL";
	public static final String SUCCESS = "SUCCESS";
	
	private String mStatus;
	private String mMessage;

	public MessageResponse() {

	}

	public MessageResponse(String status, String message) {
		mStatus = status;
		mMessage = message;
	}

	public String getStatus() {
		return mStatus;
	}

	public void setStatus(String status) {
		mStatus = status;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String message) {
		mMessage = message;
	}

}
