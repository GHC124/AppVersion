package com.ghc.appversion.domain.admin;

import java.io.Serializable;

/**
 * Select all groups and show groups that a user joined
 * 
 */
public class GroupAppCheck implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mId;
	private String mName;
	private Long mAppGroupId;

	public GroupAppCheck(Integer id, String name, Integer appGroupId) {
		mId = id.longValue();
		mName = name;
		mAppGroupId = appGroupId.longValue();
	}
	
	/**
	 * Case when userGroupId is NULL
	 */
	public GroupAppCheck(Integer id, String name, String userGroupId) {
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

	public Long getAppGroupId() {
		return mAppGroupId;
	}

	public void setAppGroupId(Long appGroupId) {
		mAppGroupId = appGroupId;
	}
}
