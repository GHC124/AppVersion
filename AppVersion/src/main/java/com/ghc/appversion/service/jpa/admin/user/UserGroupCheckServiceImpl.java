/**
 * UserServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.user;

import static com.ghc.appversion.service.jpa.admin.SQLConstants.GROUP_ID;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.LIMIT;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.OFFSET;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.ORDER_BY;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.SORT;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.USER_GROUP_CHECK_QUERY;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.appversion.domain.admin.UserGroupCheck;
import com.ghc.appversion.util.JpaUtil;

/**
 * 
 */
@Service("UserGroupCheckService")
@Repository
@Transactional
public class UserGroupCheckServiceImpl implements UserGroupCheckService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public Page<UserGroupCheck> findAllByPage(Pageable pageable, Long groupId,
			long total) {
		// TODO use setParameter
		String sql = USER_GROUP_CHECK_QUERY;
		String orderBy = "";
		String sort = "";
		Iterator<Order> i = pageable.getSort().iterator();
		while (i.hasNext()) {
			Order order = i.next();
			orderBy = order.getProperty();
			sort = order.getDirection().name();
		}
		sql = sql.replace(ORDER_BY, orderBy);
		sql = sql.replace(SORT, sort);
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(GROUP_ID, groupId);
		query.setParameter(LIMIT, pageable.getPageSize());
		query.setParameter(OFFSET, pageable.getOffset());

		List<UserGroupCheck> result = JpaUtil.getResultList(query,
				UserGroupCheck.class);
		Page<UserGroupCheck> page = new PageImpl<>(result, pageable, total);

		return page;
	}
}
