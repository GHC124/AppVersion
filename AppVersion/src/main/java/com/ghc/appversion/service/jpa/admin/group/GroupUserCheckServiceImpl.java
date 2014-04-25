/**
 * UserServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.group;

import static com.ghc.appversion.service.jpa.admin.SQLConstants.GROUP_USER_CHECK_QUERY;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.LIMIT;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.OFFSET;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.ORDER_BY;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.SORT;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.USER_ID;

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

import com.ghc.appversion.domain.admin.GroupUserCheck;
import com.ghc.appversion.util.JpaUtil;

/**
 * 
 */
@Service("GroupUserCheckService")
@Repository
@Transactional
public class GroupUserCheckServiceImpl implements GroupUserCheckService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public Page<GroupUserCheck> findAllByPage(Pageable pageable,
			Long userId, long total) {
		// TODO use setParameter
		String sql = GROUP_USER_CHECK_QUERY;
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
		query.setParameter(USER_ID, userId);
		query.setParameter(LIMIT, pageable.getPageSize());
		query.setParameter(OFFSET, pageable.getOffset());

		List<GroupUserCheck> result = JpaUtil.getResultList(query,
				GroupUserCheck.class);
		Page<GroupUserCheck> page = new PageImpl<>(result, pageable, total);

		return page;
	}
}
