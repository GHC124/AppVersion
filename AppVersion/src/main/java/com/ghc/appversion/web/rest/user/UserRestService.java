/**
 * UserRestService.java
 *
 *	
 */
package com.ghc.appversion.web.rest.user;

import com.ghc.appversion.domain.admin.User;


/**
 * 
 */
public interface UserRestService {
	User findByEmail(String email);
}
