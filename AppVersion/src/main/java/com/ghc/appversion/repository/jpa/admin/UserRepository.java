/**
 * UserRepository.java
 *
 *	
 */
package com.ghc.appversion.repository.jpa.admin;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ghc.appversion.domain.admin.User;

/**
 * 
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
}
