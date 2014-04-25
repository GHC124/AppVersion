/**
 * UserService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.UserSummary;

/**
 * 
 */
public interface UserSummaryService {
	Page<UserSummary> findAllByPage(Pageable pageable, long total, String filterGroup);
}
