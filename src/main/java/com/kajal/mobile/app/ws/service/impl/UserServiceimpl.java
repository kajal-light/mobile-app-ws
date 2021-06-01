package com.kajal.mobile.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kajal.mobile.app.ws.io.entity.UserEntity;
import com.kajal.mobile.app.ws.repo.UserRepository;
import com.kajal.mobile.app.ws.service.UserService;
import com.kajal.mobile.app.ws.shared.Utils;
import com.kajal.mobile.app.ws.ui.Exceptionerror.ErrorMessages;
import com.kajal.mobile.app.ws.ui.exception.UserServiceException;
import com.kajal.mobile.app.ws.ui.shared.dto.AddressDto;
import com.kajal.mobile.app.ws.ui.shared.dto.UserDto;
import com.kajal.mobile.app.ws.ui.shared.dto.Spring.SpringApplicationsContext;

@Service
public class UserServiceimpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserDto createUser(UserDto user) {

		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("record already found");
		for (int i = 0; i < user.getAddresses().size(); i++) {

			AddressDto address = user.getAddresses().get(i);
			address.setUserDetails(user);
			address.setAddressId(utils.genrateAddressId(30));

			user.getAddresses().set(i, address);

		}

		// UserEntity userentity = new UserEntity();

		// BeanUtils.copyProperties(user, userentity);
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userentity = modelMapper.map(user, UserEntity.class);

		String publicUserId = utils.genrateUserId(30);
		userentity.setUserid(publicUserId);

		userentity.setPassword(user.getPassword());
		userentity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userentity.setEmailVerificationToken("false");
		UserEntity storedDeatils = userRepository.save(userentity);

		// UserDto returnvaule = new UserDto();
		// BeanUtils.copyProperties(storedDeatils, returnvaule);
		UserDto returnvaule = modelMapper.map(storedDeatils, UserDto.class);

		return returnvaule;
	}

	@Override
	public UserDto getUser(String email) {

		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnvaule = new UserDto();
		BeanUtils.copyProperties(userEntity, returnvaule);
		return returnvaule;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());

	}

	@Override
	public UserDto getUserByUserid(String userid) {

		UserDto returnvaule = new UserDto();
		UserEntity userEntity = userRepository.findByUserid(userid);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
		BeanUtils.copyProperties(userEntity, returnvaule);

		return returnvaule;
	}

	public UserDto updateUser(String userid, UserDto user) {

		UserDto returnvaule = new UserDto();
		UserEntity userEntity = userRepository.findByUserid(userid);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
		userEntity.setFirstname(user.getFirstname());

		userEntity.setLastname(user.getLastname());

		UserEntity updatedUserdetails = userRepository.save(userEntity);
		BeanUtils.copyProperties(updatedUserdetails, returnvaule);
		return returnvaule;
	}

	@Override
	public void deleteUser(String userid) {
		UserEntity userEntity = userRepository.findByUserid(userid);
		if (userEntity == null)
			throw new UsernameNotFoundException(userid);

		userRepository.delete(userEntity);

	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();

		if (page > 0)
			page = page - 1;

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<UserEntity> userPage = userRepository.findAll(pageableRequest);

		List<UserEntity> users = userPage.getContent();

		for (UserEntity userEntity : users) {

			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);

		}

		return returnValue;

	}

}
