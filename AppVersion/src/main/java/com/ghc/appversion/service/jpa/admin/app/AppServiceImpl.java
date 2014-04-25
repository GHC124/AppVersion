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

import com.ghc.appversion.domain.admin.App;
import com.ghc.appversion.repository.jpa.admin.AppRepository;
import com.ghc.appversion.util.ListUtil;

/**
 * 
 */
@Service("AppService")
@Repository
@Transactional
public class AppServiceImpl implements AppService {

	@Autowired
	private AppRepository appRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#findAll()
	 */

	@Override
	@Transactional(readOnly = true)
	public List<App> findAll() {
		return ListUtil.newArrayList(appRepository.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#findById(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public App findById(Long id) {
		return appRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#save(com.ghc.domain.Group)
	 */
	@Override
	public App save(App app) {
		return appRepository.save(app);
	}

	@Override
	public Page<App> findAllByPage(Pageable pageable) {
		return appRepository.findAll(pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ghc.appversion.service.jpa.admin.GroupService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		appRepository.delete(id);
	}

	@Override
	public long count() {
		return appRepository.count();
	}
}
