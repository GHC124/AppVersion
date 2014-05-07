/**
 * UserRestServiceImpl.java
 *
 *	
 */
package com.ghc.appversion.web.rest.user;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
			String key = String.valueOf(Calendar.getInstance()
					.getTimeInMillis());
			token = EncryptUtil.makeTokenSignature(1000, username, password, key);
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
	
	@RequestMapping(value = "/findByEmail", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public RestResponse findByEmail(
			@RequestParam(value = "email", required = true) String email) {
		RestResponse response = new RestResponse();
		response.setStatus(RestResponse.SUCCESS);
		
		User user = userService.findByEmail(email);
		List<User> data = new ArrayList<User>();
		data.add(user);
		response.setData(data);
		
		return response;
	}
}
