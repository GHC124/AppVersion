/**
 * GroupServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.appversion.domain.admin.Group;
import com.ghc.appversion.repository.jpa.admin.GroupRepository;
import com.ghc.appversion.util.ListUtil;

/**
 * 
 */
@Service("GroupService")
@Repository
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#findAll()
	 */

	@Override
	@Transactional(readOnly = true)
	public List<Group> findAll() {
		return ListUtil.newArrayList(groupRepository.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#findById(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public Group findById(Long id) {
		return groupRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.GroupService#save(com.ghc.domain.Group)
	 */
	@Override
	public Group save(Group Group) {
		return groupRepository.save(Group);
	}

	@Override
	public Page<Group> findAllByPage(Pageable pageable) {
		return groupRepository.findAll(pageable);
	}

	/* (non-Javadoc)
	 * @see com.ghc.appversion.service.jpa.admin.GroupService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		groupRepository.delete(id);		
	}

	@Override
	public long count() {
		return groupRepository.count();
	}
}
