/**
 * Message.java
 *
 *	
 */
package com.ghc.appversion.web.form;

/**
 * 
 */
public class Message {
	private String mType;
	private String mMessage;

	public Message() {
	}

	public Message(String type, String message) {
		this.mType = type;
		this.mMessage = message;
	}

	public String getType() {
		return mType;
	}

	public void setType(String type) {
		this.mType = type;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String message) {
		this.mMessage = message;
	}
	
	
}
