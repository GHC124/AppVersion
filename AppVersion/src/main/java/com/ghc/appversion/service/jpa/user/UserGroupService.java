/**
 * GroupService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.user;

import com.ghc.appversion.domain.admin.UserGroup;

/**
 * 
 */
public interface UserGroupService {
	UserGroup save(UserGroup userGroup);
	
	void delete(Long id);
	
	/**
	 * Count members in a group
	 */
	long count(Long groupId);
}
