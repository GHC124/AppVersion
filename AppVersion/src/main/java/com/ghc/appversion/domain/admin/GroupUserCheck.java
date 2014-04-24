package com.ghc.appversion.domain.admin;

import java.io.Serializable;

/**
 * Select all groups and show groups that a user joined
 * 
 */
public class GroupUserCheck implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer mId;
	private String mName;
	private Integer mUserGroupId;

	public GroupUserCheck(Integer id, String name, Integer userGroupId) {
		mId = id;
		mName = name;
		mUserGroupId = userGroupId;
	}
	
	/**
	 * Case when userGroupId is NULL
	 */
	public GroupUserCheck(Integer id, String name, String userGroupId) {
		this(id, name, 0);
	}

	public Integer getId() {
		return mId;
	}

	public void setId(Integer id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public Integer getUserGroupId() {
		return mUserGroupId;
	}

	public void setUserGroupId(Integer userGroupId) {
		mUserGroupId = userGroupId;
	}
}
