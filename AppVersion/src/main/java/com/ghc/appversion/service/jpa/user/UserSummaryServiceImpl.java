/**
 * UserServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.user;

import static com.ghc.appversion.service.jpa.admin.SQLConstants.FILTER_GROUP;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.LIMIT;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.OFFSET;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.ORDER_BY;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.SORT;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.USER_SUMMARY_FILTER_GROUP_QUERY;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.USER_SUMMARY_QUERY;

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

import com.ghc.appversion.domain.admin.UserSummary;
import com.ghc.appversion.util.JpaUtil;

/**
 * 
 */
@Service("UserSummaryService")
@Repository
@Transactional
public class UserSummaryServiceImpl implements UserSummaryService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public Page<UserSummary> findAllByPage(Pageable pageable, long total,
			String filterGroup) {
		// TODO use setParameter
		String sql = USER_SUMMARY_QUERY;
		if (filterGroup != null && !filterGroup.isEmpty()) {
			sql = USER_SUMMARY_FILTER_GROUP_QUERY;
			sql = sql.replace(FILTER_GROUP, filterGroup);
		}
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
		query.setParameter(LIMIT, pageable.getPageSize());
		query.setParameter(OFFSET, pageable.getOffset());
		
		List<UserSummary> result = JpaUtil.getResultList(query,
				UserSummary.class);
		Page<UserSummary> page = new PageImpl<>(result, pageable, total);

		return page;
	}
}
