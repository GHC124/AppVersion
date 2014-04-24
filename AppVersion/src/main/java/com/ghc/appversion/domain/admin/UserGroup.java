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
 * User and Group relationship
 */
@Entity
@Table(name = "user_group")
public class UserGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mId;
	private Long mUserId;
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

	@Column(name = "user_id")
	public Long getUserId() {
		return mUserId;
	}

	public void setUserId(Long userId) {
		mUserId = userId;
	}

	@Column(name = "group_id")
	public Long getGroupId() {
		return mGroupId;
	}

	public void setGroupId(Long groupId) {
		mGroupId = groupId;
	}

}
