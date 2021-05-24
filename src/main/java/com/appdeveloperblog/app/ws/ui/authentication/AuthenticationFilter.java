package com.appdeveloperblog.app.ws.ui.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.appdeveloperblog.app.ws.service.UserService;
import com.appdeveloperblog.app.ws.service.impl.UserServiceimpl;
import com.appdeveloperblog.app.ws.ui.constants.SecurityConstants;
import com.appdeveloperblog.app.ws.ui.model.request.UserLoginRequestModel;
import com.appdeveloperblog.app.ws.ui.shared.dto.UserDto;
import com.appdeveloperblog.app.ws.ui.shared.dto.Spring.SpringApplicationsContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;

	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {

		try {

			UserLoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(),
					UserLoginRequestModel.class);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String password = creds.getPassword();
			String encodedPassword = passwordEncoder.encode(password);

			System.out.println();
			System.out.println("Password is         : " + password);
			System.out.println("Encoded Password is : " + encodedPassword);
			System.out.println();

			
			boolean isPasswordMatch = passwordEncoder.matches(password, encodedPassword);
			System.out.println("Password : " + password + "   isPasswordMatch    : " + isPasswordMatch);
		

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

		} catch (IOException e) {

			throw new RuntimeException(e);

		}
	}
@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String userName = ((User) auth.getPrincipal()).getUsername();

		String token = Jwts.builder().setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
UserService userService=(UserServiceimpl)SpringApplicationsContext.getBean("userServiceimpl");
		
UserDto userDto=userService.getUser(userName);
		
		res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PRIFIX + token);
res.addHeader("userid", userDto.getUserid());

}
}