package com.ghc.appversion.domain.admin;

import java.io.Serializable;

/**
 * Select all groups and show groups that a user joined
 * 
 */
public class GroupUserCheck implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mId;
	private String mName;
	private Long mUserGroupId;

	public GroupUserCheck(Integer id, String name, Integer userGroupId) {
		mId = id.longValue();
		mName = name;
		mUserGroupId = userGroupId.longValue();
	}
	
	/**
	 * Case when userGroupId is NULL
	 */
	public GroupUserCheck(Integer id, String name, String userGroupId) {
		this(id, name, 0);
	}

	public Long getId() {
		return mId;
	}

	public void setId(Long id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public Long getUserGroupId() {
		return mUserGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		mUserGroupId = userGroupId;
	}
}
