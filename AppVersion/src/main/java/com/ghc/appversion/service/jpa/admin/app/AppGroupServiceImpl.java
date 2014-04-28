/**
 * GroupServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.app;

import static com.ghc.appversion.service.jpa.admin.SQLConstants.GROUP_APPS_COUNT_QUERY;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.GROUP_ID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.appversion.domain.admin.AppGroup;
import com.ghc.appversion.repository.jpa.admin.AppGroupRepository;
import com.ghc.appversion.util.JpaUtil;

/**
 * 
 */
@Service("AppGroupService")
@Repository
@Transactional
public class AppGroupServiceImpl implements AppGroupService {

	@Autowired
	private AppGroupRepository appGroupRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ghc.appversion.service.jpa.admin.AppGroupService#save(com.ghc.appversion
	 * .domain.admin.AppGroup)
	 */
	@Override
	public AppGroup save(AppGroup appGroup) {
		return appGroupRepository.save(appGroup);
	}

	/* (non-Javadoc)
	 * @see com.ghc.appversion.service.jpa.admin.AppGroupService#delete(com.ghc.appversion.domain.admin.AppGroup)
	 */
	@Override
	public void delete(Long id) {
		appGroupRepository.delete(id);
		
	}

	/* (non-Javadoc)
	 * @see com.ghc.appversion.service.jpa.admin.AppGroupService#count(java.lang.Long)
	 */
	@Override
	public long count(Long groupId) {
		String sql = GROUP_APPS_COUNT_QUERY;		
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(GROUP_ID, groupId);
		
		Long result = JpaUtil.getSingleResult(query, Long.class);

		return result.longValue();
	}
}
