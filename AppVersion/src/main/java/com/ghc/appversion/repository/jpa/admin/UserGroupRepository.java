/**
 * GroupRepository.java
 *
 *	
 */
package com.ghc.appversion.repository.jpa.admin;

import org.springframework.data.repository.CrudRepository;

import com.ghc.appversion.domain.admin.UserGroup;

/**
 * 
 */
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

}
