/**
 * UserSummary.java
 *
 *	
 */
package com.ghc.appversion.domain.admin;

import java.io.Serializable;

/**
 * Select all users information(email, groups)
 */
public class GroupSummary implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mId;
	private String mName;
	private Long mMembers;

	public GroupSummary(Integer id, String name, Long members) {
		mId = id.longValue();
		mName = name;
		mMembers = members;
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

	public Long getMembers() {
		return mMembers;
	}

	public void setMembers(Long members) {
		mMembers = members;
	}

}
