/**
 * GroupService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.app;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.AppVersions;

/**
 * 
 */
public interface AppVersionsService {
	List<AppVersions> findAll();

	AppVersions findById(Long id);

	AppVersions save(AppVersions appVersion);

	Page<AppVersions> findAllByPage(Pageable pageable);
	
	void delete(Long id);

	long count();
	
	/**
	 * Get latest version by Release Date
	 * @param appId
	 * @return
	 */
	List<AppVersions> latestVersion(Long appId);
	
	/**
	 * Find all version of app
	 */
	List<AppVersions> findAllByAppId(Long appId);
	
	/**
	 * Find all version of app
	 */
	Page<AppVersions> findAllByAppId(Pageable pageable, Long appId, long total);
}
