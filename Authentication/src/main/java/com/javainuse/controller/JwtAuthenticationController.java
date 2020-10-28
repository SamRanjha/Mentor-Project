package com.javainuse.controller;

import java.util.Objects;
import java.util.logging.Logger;

import com.javainuse.dao.UserDao;
import com.javainuse.model.DAOUser;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.javainuse.service.JwtUserDetailsService;


import com.javainuse.config.JwtTokenUtil;
import com.javainuse.model.JwtRequest;
import com.javainuse.model.JwtResponse;
import com.javainuse.model.UserDTO;

class Token{
	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

class Response{
	private String username;
	private boolean tokenValid;

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isTokenStatus() {
		return tokenValid;
	}

	public void setTokenStatus(boolean tokenStatus) {
		this.tokenValid = tokenStatus;
	}
}

@RestController
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserDao userDao;

	org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/generateToken", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Boolean> saveUser(@RequestBody UserDTO user) throws Exception {
		DAOUser duser = userDao.findByUsername(user.getUsername());
		if (duser != null) {
			logger.error("USER name already taken");
			return null;
		}
		userDetailsService.save(user);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@RequestMapping(value = "/valid", method = RequestMethod.POST)
	public Response valid(@RequestBody Token token ){
		Response r = new Response();
		String jwtToken = token.token;
		System.out.println(jwtToken);
		String username = "";
		if (jwtToken.startsWith("Bearer ")) {
			jwtToken = jwtToken.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
				r.setUsername("");
				r.setTokenStatus(false);
				return r;
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
				r.setUsername("");
				r.setTokenStatus(false);
				return r;
			}catch (Exception e){
				System.out.println(e);
				r.setUsername("");
				r.setTokenStatus(false);
				return r;
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
		boolean b =  true;
		try{
		b = jwtTokenUtil.validateToken(jwtToken,userDetails);
		}catch (Exception e){
			System.out.println(e);
			r.setUsername("");
			r.setTokenStatus(false);
			return r;
		}
		System.out.println(b);
		r.setUsername(username);
		r.setTokenStatus(true);
		return r;
	}

}