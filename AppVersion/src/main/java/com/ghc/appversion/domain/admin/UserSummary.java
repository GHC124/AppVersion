/**
 * UserSummary.java
 *
 *	
 */
package com.ghc.appversion.domain.admin;

import java.io.Serializable;

/**
 * Select all users information(email, groups)
 */
public class UserSummary implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mUserId;
	private String mEmail;
	private String mGroupNames;	
	
	public UserSummary(Integer userId, String email, String groupNames) {
		mUserId = userId;
		mEmail = email;
		mGroupNames = groupNames;
	}

	public Integer getUserId() {
		return mUserId;
	}

	public void setUserId(Integer userId) {
		mUserId = userId;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		mEmail = email;
	}

	public String getGroupNames() {
		return mGroupNames;
	}

	public void setGroupNames(String groupNames) {
		mGroupNames = groupNames;
	}
	
}
