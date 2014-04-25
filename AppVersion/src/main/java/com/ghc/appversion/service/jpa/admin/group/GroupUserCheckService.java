/**
 * UserService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.GroupUserCheck;

/**
 * 
 */
public interface GroupUserCheckService {
	Page<GroupUserCheck> findAllByPage(Pageable pageable, Long userId, long total);
}
