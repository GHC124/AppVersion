/**
 * UserService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.app;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.AppSummary;

/**
 * 
 */
public interface AppSummaryService {
	Page<AppSummary> findAllByPage(Pageable pageable, long total);
}
