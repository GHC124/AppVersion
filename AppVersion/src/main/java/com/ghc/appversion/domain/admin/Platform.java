/**
 * Platform.java
 *
 *	
 */
package com.ghc.appversion.domain.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ghc.appversion.domain.BaseEntity;

/**
 * 
 */
@Entity
@Table(name = "platform")
public class Platform extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mId;
	private String mName;
	private String mPlatformType;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId() {
		return mId;
	}

	public void setId(Long id) {
		mId = id;
	}

	@Column(name="name")
	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	@Column(name="platform_type")
	public String getPlatformType() {
		return mPlatformType;
	}

	public void setPlatformType(String platformType) {
		mPlatformType = platformType;
	}

}
