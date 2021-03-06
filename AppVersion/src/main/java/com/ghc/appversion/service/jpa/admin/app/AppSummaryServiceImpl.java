/**
 * AppServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.service.jpa.admin.app;

import static com.ghc.appversion.service.jpa.admin.SQLConstants.APP_SUMMARY_QUERY;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.LIMIT;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.OFFSET;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.ORDER_BY;
import static com.ghc.appversion.service.jpa.admin.SQLConstants.SORT;

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

import com.ghc.appversion.domain.admin.AppSummary;
import com.ghc.appversion.util.JpaUtil;

/**
 * 
 */
@Service("AppSummaryService")
@Repository
@Transactional
public class AppSummaryServiceImpl implements AppSummaryService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public Page<AppSummary> findAllByPage(Pageable pageable, long total) {
		// TODO use setParameter
		String sql = APP_SUMMARY_QUERY;		
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

		List<AppSummary> result = JpaUtil
				.getResultList(query, AppSummary.class);
		Page<AppSummary> page = new PageImpl<>(result, pageable, total);

		return page;
	}
}
