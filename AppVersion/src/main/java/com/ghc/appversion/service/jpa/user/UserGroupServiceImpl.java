/**
 * GroupServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.user;

import static com.ghc.appversion.service.jpa.admin.SQLConstants.GROUP_ID;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.GROUP_MEMBERS_COUNT_QUERY;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.appversion.domain.admin.UserGroup;
import com.ghc.appversion.repository.jpa.admin.UserGroupRepository;
import com.ghc.appversion.util.JpaUtil;

/**
 * 
 */
@Service("UserGroupService")
@Repository
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private UserGroupRepository userGroupRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ghc.appversion.service.jpa.admin.UserGroupService#save(com.ghc.appversion
	 * .domain.admin.UserGroup)
	 */
	@Override
	public UserGroup save(UserGroup userGroup) {
		return userGroupRepository.save(userGroup);
	}

	/* (non-Javadoc)
	 * @see com.ghc.appversion.service.jpa.admin.UserGroupService#delete(com.ghc.appversion.domain.admin.UserGroup)
	 */
	@Override
	public void delete(Long id) {
		userGroupRepository.delete(id);
		
	}

	/* (non-Javadoc)
	 * @see com.ghc.appversion.service.jpa.admin.UserGroupService#count(java.lang.Long)
	 */
	@Override
	public long count(Long groupId) {
		String sql = GROUP_MEMBERS_COUNT_QUERY;		
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(GROUP_ID, groupId);
		
		Long result = JpaUtil.getSingleResult(query, Long.class);

		return result.longValue();
	}
}
