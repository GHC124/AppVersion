/**
 * UserRestServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.web.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ghc.appversion.domain.admin.User;
import com.ghc.appversion.service.jpa.admin.user.UserService;

/**
 * 
 */
@Controller
@RequestMapping("/rest/users")
public class UserRestController {

	@Autowired
	private UserService userService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ghc.appversion.web.rest.user.UserRestService#findByEmail(java.lang
	 * .String)
	 */
	@RequestMapping(value = "/findByEmail/{email}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public User findByEmail(@PathVariable("email") String email) {
		return userService.findByEmail(email);
	}

}
