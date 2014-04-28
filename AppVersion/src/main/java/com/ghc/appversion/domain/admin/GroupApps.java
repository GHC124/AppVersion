/**
 * GroupMembers.java
 *
 *	
 */
package com.ghc.appversion.domain.admin;

import java.io.Serializable;

/**
 * Apps in a group
 */
public class GroupApps implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mAppId;
	private String mName;
	private Long mAppGroupId;

	public GroupApps(Integer appId, String name, Integer appGroupId) {
		mAppId = appId.longValue();
		mName = name;
		mAppGroupId = appGroupId.longValue();
	}

	public Long getAppId() {
		return mAppId;
	}

	public void setAppId(Long appId) {
		mAppId = appId;
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
