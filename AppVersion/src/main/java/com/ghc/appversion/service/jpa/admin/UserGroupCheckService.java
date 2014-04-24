/**
 * UserService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.UserGroupCheck;

/**
 * 
 */
public interface UserGroupCheckService {
	Page<UserGroupCheck> findAllByPage(Pageable pageable, Long groupId, long total);
}
