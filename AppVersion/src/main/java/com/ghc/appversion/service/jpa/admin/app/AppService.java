/**
 * GroupService.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.app;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.appversion.domain.admin.App;

/**
 * 
 */
public interface AppService {
	List<App> findAll();

	App findById(Long id);

	App save(App platform);

	Page<App> findAllByPage(Pageable pageable);

	void delete(Long id);

	long count();	
}
