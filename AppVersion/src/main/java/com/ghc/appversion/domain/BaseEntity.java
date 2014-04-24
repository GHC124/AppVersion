package com.ghc.appversion.domain;

import javax.persistence.Column;
import javax.persistence.Version;

public abstract class BaseEntity {
	protected int mVersion;

	@Version
	@Column(name="version")
	public int getVersion() {
		return mVersion;
	}

	public void setVersion(int version) {
		mVersion = version;
	}
	
	
}
