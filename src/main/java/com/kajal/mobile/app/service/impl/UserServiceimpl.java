package com.kajal.mobile.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.kajal.mobile.app.io.entity.UserEntity;
import com.kajal.mobile.app.repo.UserRepository;
import com.kajal.mobile.app.service.UserService;
import com.kajal.mobile.app.shared.Utils;
import com.kajal.mobile.app.ui.Exceptionerror.ErrorMessages;
import com.kajal.mobile.app.ui.exception.ErrorMessage;
import com.kajal.mobile.app.ui.exception.UserServiceException;
import com.kajal.mobile.app.ui.model.response.UserRest;
import com.kajal.mobile.app.ui.shared.dto.UserDto;

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
	public UserDto getUserByUserid(String userid) {

		UserDto returnvaule = new UserDto();
		UserEntity userEntity = userReposit.findByUserid(userid);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
		BeanUtils.copyProperties(userEntity, returnvaule);

		return returnvaule;
	}

	public UserDto updateUser(String userid, UserDto user) {

		UserDto returnvaule = new UserDto();
		UserEntity userEntity = userReposit.findByUserid(userid);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
		userEntity.setFirstname(user.getFirstname());

		userEntity.setLastname(user.getLastname());

		UserEntity updatedUserdetails = userReposit.save(userEntity);
		BeanUtils.copyProperties(updatedUserdetails, returnvaule);
		return returnvaule;
	}

	@Override
	public void deleteUser(String userid) {
		UserEntity userEntity = userReposit.findByUserid(userid);
		if (userEntity == null)
			throw new UsernameNotFoundException(userid);

		userReposit.delete(userEntity);

	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();

		if (page > 0)
			page = page - 1;

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<UserEntity> userPage = userReposit.findAll(pageableRequest);

		List<UserEntity> users = userPage.getContent();

		for (UserEntity userEntity : users) {

			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);

		}

		return returnValue;

	}

}
