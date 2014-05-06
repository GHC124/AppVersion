/**
 * UserRestServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.web.rest.user;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ghc.appversion.domain.admin.User;
import com.ghc.appversion.service.jpa.admin.user.UserService;
import com.ghc.appversion.util.EncryptUtil;
import com.ghc.appversion.util.LogUtil;
import com.ghc.appversion.web.rest.RestResponse;

/**
 * 
 */
@Controller
@RequestMapping("/rest/users")
@PreAuthorize("isAuthenticated()")
public class UserRestController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public RestResponse login(
			@RequestParam(value = "j_username", required = true) String username,
			@RequestParam(value = "j_password", required = true) String password) {
		RestResponse response = new RestResponse();
		response.setStatus(RestResponse.FAIL);

		// generate token
		String token = "";
		try {
			token = EncryptUtil.makeTokenSignature(1000, username, password,
					"token");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (!token.isEmpty()) {
			// update user
			boolean success = userService.updateLoginToken(username, token);
			if (success) {
				response.setStatus(RestResponse.SUCCESS);
				response.setMessage(token);
			}
		}
		
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ghc.appversion.web.rest.user.UserRestService#findByEmail(java.lang
	 * .String)
	 */
	@RequestMapping(value = "/findByEmail", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public User findByEmail(
			@RequestParam(value = "email", required = true) String email) {
		LogUtil.error("Email: " + email);
		return userService.findByEmail(email);
	}

}
