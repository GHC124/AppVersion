/**
 * GroupServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.app;

import static com.ghc.appversion.service.jpa.admin.SQLConstants.APP_NAME;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.APP_SELECT_BY_NAME_QUERY;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.appversion.domain.admin.App;
import com.ghc.appversion.repository.jpa.admin.AppRepository;
import com.ghc.appversion.util.JpaUtil;
import com.ghc.appversion.util.ListUtil;

/**
 * 
 */
@Service("AppService")
@Repository
@Transactional
public class AppServiceImpl implements AppService {

	@PersistenceContext
	private EntityManager entityManager;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ghc.appversion.service.jpa.admin.app.AppService#findByName(java.lang
	 * .String)
	 */
	@Override
	public App findByName(String name) {
		// TODO use setParameter
		String sql = APP_SELECT_BY_NAME_QUERY;
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(APP_NAME, "%" + name + "%");

		List<App> result = JpaUtil.getResultList(query, App.class);
		if (result == null || result.size() == 0) {
			return null;
		}

		return result.get(0);
	}
}
