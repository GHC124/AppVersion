/**
 * GroupService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin;

import com.ghc.appversion.domain.admin.UserGroup;

/**
 * 
 */
public interface UserGroupService {
	UserGroup save(UserGroup userGroup);
	
	void delete(Long id);
}
