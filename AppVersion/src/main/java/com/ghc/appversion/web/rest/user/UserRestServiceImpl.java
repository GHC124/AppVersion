/**
 * UserRestServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.web.rest.user;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghc.appversion.domain.admin.User;
import com.ghc.appversion.service.jpa.admin.user.UserService;

/**
 * 
 */
@Component
@Path("/user")
public class UserRestServiceImpl implements UserRestService {

	@Autowired
	private UserService userService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ghc.appversion.web.rest.user.UserRestService#findByEmail(java.lang
	 * .String)
	 */
	@Override
	@GET
	@Path("/findByEmail/{email}")
	public User findByEmail(@PathParam("email") String email) {
		return userService.findByEmail(email);
	}

}
