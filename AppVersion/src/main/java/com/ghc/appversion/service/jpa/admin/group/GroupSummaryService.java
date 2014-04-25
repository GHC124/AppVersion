/**
 * UserService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.GroupSummary;

/**
 * 
 */
public interface GroupSummaryService {
	Page<GroupSummary> findAllByPage(Pageable pageable, long total);
}
