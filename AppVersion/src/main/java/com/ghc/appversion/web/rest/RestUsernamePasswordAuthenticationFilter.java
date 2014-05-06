/**
 * RestUsernamePasswordAuthenticationFilter.java
 *
 *	
 */
package com.ghc.appversion.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ghc.appversion.service.jpa.admin.user.UserService;

/**
 * 
 */
public class RestUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	@Autowired
	private UserService userService;

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		boolean retVal = false;
		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		String token = request.getParameter("j_token");
		if (username != null) {
			Authentication authResult = null;
			if (password != null) {
				try {
					authResult = attemptAuthentication(request, response);
				} catch (AuthenticationException failed) {
					try {
						unsuccessfulAuthentication(request, response, failed);
					} catch (IOException | ServletException e) {
						e.printStackTrace();
					}
				}
			} else if (token != null) {
				com.ghc.appversion.domain.admin.User userEntity = userService
						.findByEmail(username);
				if (userEntity == null) {
					userEntity = new com.ghc.appversion.domain.admin.User();
				}
				String loginToken = userEntity.getLoginToken();
				if (loginToken != null && loginToken.equals(token)) {
					User user = buildUserFromUserEntity(userEntity);
					authResult = new UsernamePasswordAuthenticationToken(user,
							null, user.getAuthorities());
				} else {
					try {
						unsuccessfulAuthentication(request, response,
								new BadCredentialsException("Bad Credentials"));
					} catch (IOException | ServletException e) {
						e.printStackTrace();
					}
				}
			}
			if (authResult != null) {
				try {
					successfulAuthentication(request, response, null,
							authResult);
				} catch (IOException | ServletException e) {
					e.printStackTrace();
				}
			}
		} else {
			retVal = true;
		}

		return retVal;
	}

	private User buildUserFromUserEntity(
			com.ghc.appversion.domain.admin.User userEntity) {
		String username = userEntity.getEmail();
		String password = userEntity.getPassword();
		boolean enabled = userEntity.getIsActive() != null
				&& userEntity.getIsActive() > 0;
		boolean accountNonExpired = enabled;
		boolean credentialsNonExpired = enabled;
		boolean accountNonLocked = enabled;

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(userEntity.getAuthority()));

		User user = new User(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		return user;
	}
}
