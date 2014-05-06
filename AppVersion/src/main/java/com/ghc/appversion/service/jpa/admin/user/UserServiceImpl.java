/**
 * UserServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.appversion.domain.admin.User;
import com.ghc.appversion.repository.jpa.admin.UserRepository;
import com.ghc.appversion.util.EncryptUtil;
import com.ghc.appversion.util.ListUtil;

/**
 * 
 */
@Service("UserService")
@Repository
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.UserService#findAll()
	 */

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return ListUtil.newArrayList(userRepository.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.UserService#findById(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ghc.service.jpa.UserService#save(com.ghc.domain.User)
	 */
	@Override
	public User save(User user) {
		if (user.getId() == null || user.getId() <= 0) {
			// Encrypt password when add new user
			try {
				user.setPassword(EncryptUtil.generatePBKDF2(user.getPassword()));
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				throw new RuntimeException("Fail to encrypt password", e);
			}
		}
		return userRepository.save(user);
	}

	@Override
	public Page<User> findAllByPage(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public long count() {
		return userRepository.count();
	}

	/* (non-Javadoc)
	 * @see com.ghc.appversion.service.jpa.admin.user.UserService#findByEmail(java.lang.String)
	 */
	@Override
	public User findByEmail(String email) {
		TypedQuery<User> query = entityManager.createNamedQuery("User.findByEmail", User.class);
		query.setParameter("email", email);		
		List<User> users = query.getResultList();
		if(users == null || users.size() == 0) {
			return null;
		}
		return users.get(0);
	}

	/* (non-Javadoc)
	 * @see com.ghc.appversion.service.jpa.admin.user.UserService#updateToken(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateLoginToken(String email, String token) {
		Query query = entityManager.createNamedQuery("User.updateLoginToken");
		query.setParameter("email", email);		
		query.setParameter("loginToken", token);
		int result = query.executeUpdate();
		
		return result > 0;
	}

}
