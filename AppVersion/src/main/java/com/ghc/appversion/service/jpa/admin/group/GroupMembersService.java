/**
 * GroupService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.GroupMembers;

/**
 * 
 */
public interface GroupMembersService {
	Page<GroupMembers> findAllByPage(Pageable pageable, Long groupId, long total);
}
