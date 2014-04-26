package com.ghc.appversion.domain;

import javax.persistence.Column;
import javax.persistence.Version;

public abstract class BaseEntity {
	protected Long mVersion;

	public BaseEntity() {
		
	}
	
	public BaseEntity(Long version) {
		mVersion = version;
	}

	@Version
	@Column(name="version")
	public Long getVersion() {
		return mVersion;
	}

	public void setVersion(Long version) {
		mVersion = version;
	}
	
	
}
