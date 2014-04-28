/**
 * UserService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.GroupAppCheck;

/**
 * 
 */
public interface GroupAppCheckService {
	Page<GroupAppCheck> findAllByPage(Pageable pageable, Long appId, long total);
}
