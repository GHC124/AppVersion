/**
 * AppSummary.java
 *
 *	
 */
package com.ghc.appversion.domain.admin;

import java.io.Serializable;

/**
 * 
 */
public class AppSummary implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mId;
	private String mName;
	private String mLatestVersion;
	private Long mPlatformId;
	private Long mVersions;

	public AppSummary() {

	}

	public AppSummary(Integer id, String name, String latestVersion,
			Integer platformId, Long versions) {
		mId = id.longValue();
		mName = name;
		mLatestVersion = latestVersion;
		mPlatformId = platformId.longValue();
		mVersions = versions;
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

	public String getLatestVersion() {
		return mLatestVersion;
	}

	public void setLatestVersion(String latestVersion) {
		mLatestVersion = latestVersion;
	}

	public Long getPlatformId() {
		return mPlatformId;
	}

	public void setPlatformId(Long platformId) {
		mPlatformId = platformId;
	}

	public Long getVersions() {
		return mVersions;
	}

	public void setVersions(Long versions) {
		mVersions = versions;
	}

}
