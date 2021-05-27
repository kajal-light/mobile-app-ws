package com.appdeveloperblog.app.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.appdeveloperblog.app.ws.ui.shared.dto.UserDto;
@Service
public interface UserService extends UserDetailsService {

	UserDto createUser(UserDto user);
	UserDto getUser(String email);


	
	UserDto findUserByUserid(String userId);

	UserDto updateUser(String userId, UserDto userDto);

	
	

	
}



