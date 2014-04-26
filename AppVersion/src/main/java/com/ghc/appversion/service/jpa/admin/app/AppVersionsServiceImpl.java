/**
 * GroupServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.appversion.domain.admin.AppVersions;
import com.ghc.appversion.repository.jpa.admin.AppVersionsRepository;
import com.ghc.appversion.util.ListUtil;

/**
 * 
 */
@Service("AppVersionsService")
@Repository
@Transactional
public class AppVersionsServiceImpl implements AppVersionsService {

	@Autowired
	private AppVersionsRepository appVersionsRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#findAll()
	 */

	@Override
	@Transactional(readOnly = true)
	public List<AppVersions> findAll() {
		return ListUtil.newArrayList(appVersionsRepository.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#findById(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public AppVersions findById(Long id) {
		return appVersionsRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#save(com.ghc.domain.Group)
	 */
	@Override
	public AppVersions save(AppVersions appVersion) {
		return appVersionsRepository.save(appVersion);
	}

	@Override
	public Page<AppVersions> findAllByPage(Pageable pageable) {
		return appVersionsRepository.findAll(pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ghc.appversion.service.jpa.admin.GroupService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		appVersionsRepository.delete(id);
	}

	@Override
	public long count() {
		return appVersionsRepository.count();
	}
}
