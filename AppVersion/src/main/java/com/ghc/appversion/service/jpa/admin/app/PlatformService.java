/**
 * GroupService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.app;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.Platform;

/**
 * 
 */
public interface PlatformService {
	List<Platform> findAll();

	Platform findById(Long id);

	Platform save(Platform platform);

	Page<Platform> findAllByPage(Pageable pageable);

	void delete(Long id);

	long count();
}
