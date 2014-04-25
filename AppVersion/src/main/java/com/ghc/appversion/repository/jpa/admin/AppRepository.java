/**
 * GroupRepository.java
 *
 *	
 */
package com.ghc.appversion.repository.jpa.admin;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ghc.appversion.domain.admin.App;


public interface AppRepository extends
		PagingAndSortingRepository<App, Long> {

}
