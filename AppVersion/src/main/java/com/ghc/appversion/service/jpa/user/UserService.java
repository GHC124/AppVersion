/**
 * UserService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.User;

/**
 * 
 */
public interface UserService {
	long count();
	
	List<User> findAll();

	User findById(Long id);

	User save(User user);

	Page<User> findAllByPage(Pageable pageable);
}