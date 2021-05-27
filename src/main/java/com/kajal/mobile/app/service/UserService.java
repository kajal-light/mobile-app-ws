package com.kajal.mobile.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kajal.mobile.app.ui.shared.dto.UserDto;
@Service
public interface UserService extends UserDetailsService {

	UserDto createUser(UserDto user);
	UserDto getUser(String email);


	

	UserDto updateUser(String userid, UserDto userDto);
 void deleteUser(String userid); 
	// TODO Auto-generated method stub
UserDto getUserByUserid(String userid);
List<UserDto> getUsers(int page, int limit);

	
	

	
}



