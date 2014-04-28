/**
 * UserGroup.java
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

/**
 * App and Group relationship
 */
@Entity
@Table(name = "app_group")
public class AppGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mId;
	private Long mAppId;
	private Long mGroupId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return mId;
	}

	public void setId(Long id) {
		mId = id;
	}

	@Column(name = "app_id")
	public Long getAppId() {
		return mAppId;
	}

	public void setAppId(Long appId) {
		mAppId = appId;
	}

	@Column(name = "group_id")
	public Long getGroupId() {
		return mGroupId;
	}

	public void setGroupId(Long groupId) {
		mGroupId = groupId;
	}

}
