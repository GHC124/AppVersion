/**
 * GroupRepository.java
 *
 *	
 */
package com.ghc.appversion.repository.jpa.admin;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ghc.appversion.domain.admin.AppVersions;


public interface AppVersionsRepository extends
		PagingAndSortingRepository<AppVersions, Long> {

}
