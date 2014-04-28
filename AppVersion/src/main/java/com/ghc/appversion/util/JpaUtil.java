/**
 * JpaUtil.java
 *
 *	
 */
package com.ghc.appversion.util;

import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * 
 */
public class JpaUtil {
	private static <T> T map(Class<T> type, Object[] tuple) {
		List<Class<?>> tupleTypes = new ArrayList<>();
		for (int i = 0; i < tuple.length; i++) {
			if (tuple[i] != null) {
				if (tuple[i] instanceof BigInteger) {
					tuple[i] = new Long(((BigInteger) tuple[i]).longValue());
				}
				if (tuple[i] instanceof Date) {
					tuple[i] = new DateTime(tuple[i], DateTimeZone.getDefault());					
				}
				tupleTypes.add(tuple[i].getClass());
			} else {
				tupleTypes.add(String.class);
			}
		}
		try {
			Constructor<T> ctor = type.getConstructor(tupleTypes
					.toArray(new Class<?>[tuple.length]));
			return ctor.newInstance(tuple);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> List<T> map(Class<T> type, List<Object[]> records) {
		List<T> result = new LinkedList<>();
		for (Object[] record : records) {
			result.add(map(type, record));
		}
		return result;
	}

	public static <T> List<T> getResultList(Query query, Class<T> type) {
		@SuppressWarnings("unchecked")
		List<Object[]> records = query.getResultList();
		return map(type, records);
	}

	public static <T> T getSingleResult(Query query, Class<T> type) {
		Object object = query.getSingleResult();
		if (object instanceof BigInteger) {
			object = ((BigInteger) object).longValue();
		}
		return type.cast(object);
	}
}
