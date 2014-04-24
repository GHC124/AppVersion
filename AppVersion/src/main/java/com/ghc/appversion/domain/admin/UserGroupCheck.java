package com.ghc.appversion.domain.admin;

import java.io.Serializable;

/**
 * Select all users and show that user joined a specific group or not
 * 
 */
public class UserGroupCheck implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mId;
	private String mEmail;
	private Long mUserGroupId;

	public UserGroupCheck(Integer id, String email, Integer userGroupId) {
		mId = id.longValue();
		mEmail = email;
		mUserGroupId = userGroupId.longValue();
	}
	
	/**
	 * Case when userGroupId is NULL
	 */
	public UserGroupCheck(Integer id, String email, String userGroupId) {
		this(id, email, 0);
	}

	public Long getId() {
		return mId;
	}

	public void setId(Long id) {
		mId = id;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		mEmail = email;
	}

	public Long getUserGroupId() {
		return mUserGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		mUserGroupId = userGroupId;
	}
}
