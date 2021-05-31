package com.kajal.mobile.app.ws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kajal.mobile.app.ws.ui.shared.dto.UserDto;

@Service
public interface UserService extends UserDetailsService {

	UserDto createUser(UserDto user);

	UserDto getUser(String email);

	UserDto updateUser(String userid, UserDto userDto);

	void deleteUser(String userid);

	UserDto getUserByUserid(String userid);

	List<UserDto> getUsers(int page, int limit);

}
