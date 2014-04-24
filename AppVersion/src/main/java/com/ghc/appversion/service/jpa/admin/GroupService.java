/**
 * GroupService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.Group;

/**
 * 
 */
public interface GroupService {
	List<Group> findAll();

	Group findById(Long id);

	Group save(Group group);

	Page<Group> findAllByPage(Pageable pageable);
	
	void delete(Long id);
	
	long count();
}
