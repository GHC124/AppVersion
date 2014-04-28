/**
 * GroupService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.app;

import com.ghc.appversion.domain.admin.AppGroup;

/**
 * 
 */
public interface AppGroupService {
	AppGroup save(AppGroup appGroup);
	
	void delete(Long id);
	
	/**
	 * Count members in a group
	 */
	long count(Long groupId);
}
