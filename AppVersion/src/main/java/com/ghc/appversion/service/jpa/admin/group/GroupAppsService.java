/**
 * GroupService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.GroupApps;

/**
 * 
 */
public interface GroupAppsService {
	Page<GroupApps> findAllByPage(Pageable pageable, Long groupId, long total);
}
