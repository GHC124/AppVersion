/**
 * MessageResponse.java
 *
 *	
 */
package com.ghc.appversion.web.rest;

import java.util.List;

/**
 * 
 */
public class RestResponse {
	public static final String FAIL = "FAIL";
	public static final String SUCCESS = "SUCCESS";

	private String mStatus;
	private String mMessage;
	private List<?> mData;

	public RestResponse() {

	}

	public RestResponse(String status, String message) {
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

	public List<?> getData() {
		return mData;
	}

	public void setData(List<?> data) {
		mData = data;
	}

}
