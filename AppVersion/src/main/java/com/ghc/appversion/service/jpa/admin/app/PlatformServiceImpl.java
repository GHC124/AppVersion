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

import com.ghc.appversion.domain.admin.Platform;
import com.ghc.appversion.repository.jpa.admin.PlatformRepository;
import com.ghc.appversion.util.ListUtil;

/**
 * 
 */
@Service("PlatformService")
@Repository
@Transactional
public class PlatformServiceImpl implements PlatformService {

	@Autowired
	private PlatformRepository platformRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#findAll()
	 */

	@Override
	@Transactional(readOnly = true)
	public List<Platform> findAll() {
		return ListUtil.newArrayList(platformRepository.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#findById(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public Platform findById(Long id) {
		return platformRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#save(com.ghc.domain.Group)
	 */
	@Override
	public Platform save(Platform platform) {
		return platformRepository.save(platform);
	}

	@Override
	public Page<Platform> findAllByPage(Pageable pageable) {
		return platformRepository.findAll(pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ghc.appversion.service.jpa.admin.GroupService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		platformRepository.delete(id);
	}

	@Override
	public long count() {
		return platformRepository.count();
	}
}
