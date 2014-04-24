/**
 * GroupMembers.java
 *
 *	
 */
package com.ghc.appversion.domain.admin;

import java.io.Serializable;

/**
 * Members in a group
 */
public class GroupMembers implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mUserId;
	private String mEmail;
	private Long mUserGroupId;

	public GroupMembers(Integer userId, String email, Integer userGroupId) {
		mUserId = userId.longValue();
		mEmail = email;
		mUserGroupId = userGroupId.longValue();
	}

	public Long getUserId() {
		return mUserId;
	}

	public void setUserId(Long userId) {
		mUserId = userId;
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
