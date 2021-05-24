package com.appdeveloperblog.app.ws.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.appdeveloperblog.app.ws.io.entity.UserEntity;
import com.appdeveloperblog.app.ws.repo.UserRepository;
import com.appdeveloperblog.app.ws.service.UserService;
import com.appdeveloperblog.app.ws.shared.Utils;
import com.appdeveloperblog.app.ws.ui.shared.dto.UserDto;

@Component
public class UserServiceimpl implements UserService {
	@Autowired
	UserRepository userReposit;
	@Autowired

	Utils utils;

	@Autowired

	BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserDto createUser(UserDto user) {
//		UserEntity userentity1 = userReposit.findByEmail(user.getEmail());
//		if(userentity1 != null) {
//			throw new RuntimeException("record is already exist");
//		}
//		

		UserEntity userentity = new UserEntity();

		BeanUtils.copyProperties(user, userentity);

		String publicUserId = utils.genrateUserId(30);
		userentity.setUserid(publicUserId);

		userentity.setPassword(user.getPassword());
		userentity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userentity.setEmailVerificationToken("false");
		UserEntity storedDeatils = userReposit.save(userentity);

		UserDto returnvaule = new UserDto();
		BeanUtils.copyProperties(storedDeatils, returnvaule);

		return returnvaule;
	}

	@Override
	public UserDto getUser(String email) {

		UserEntity userEntity = userReposit.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnvaule = new UserDto();
		BeanUtils.copyProperties(userEntity, returnvaule);
		return returnvaule;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = userReposit.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());

	}
	

	@Override
	public UserDto findUserByUserid(String userId) {

		UserDto returnvaule = new UserDto();
		UserEntity userEntity = userReposit.findByUserid(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException(userId);
		BeanUtils.copyProperties(userEntity, returnvaule);

		return returnvaule;
	}

}
