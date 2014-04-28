/**
 * UserService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.app;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.AppGroupCheck;

/**
 * 
 */
public interface AppGroupCheckService {
	Page<AppGroupCheck> findAllByPage(Pageable pageable, Long groupId, long total);
}
