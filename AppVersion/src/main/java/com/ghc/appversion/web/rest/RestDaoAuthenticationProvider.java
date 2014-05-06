/**
 * RestDaoAuthenticationProvider.java
 *
 *	
 */
package com.ghc.appversion.web.rest;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ghc.appversion.util.EncryptUtil;

/**
 * 
 */
public class RestDaoAuthenticationProvider extends DaoAuthenticationProvider {
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		String username = String.valueOf(auth.getPrincipal());
		String password = String.valueOf(auth.getCredentials());

		UserDetailsService userDetailsService = getUserDetailsService();
		UserDetails user = userDetailsService.loadUserByUsername(username);

		// Check the passwords match.
		boolean valid = false;
		try {
			valid = EncryptUtil.validatePBKDF2(password, user.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		if (!valid) {
			throw new BadCredentialsException("Bad Credentials");
		}

		// 4. Return an authenticated token, containing user data and
		// authorities
		return new UsernamePasswordAuthenticationToken(user, null,
				user.getAuthorities());
	}
}
