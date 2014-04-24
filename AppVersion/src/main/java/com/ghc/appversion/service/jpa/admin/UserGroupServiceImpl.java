/**
 * GroupServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.appversion.domain.admin.UserGroup;
import com.ghc.appversion.repository.jpa.admin.UserGroupRepository;

/**
 * 
 */
@Service("UserGroupService")
@Repository
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private UserGroupRepository userGroupRepository;

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
}
