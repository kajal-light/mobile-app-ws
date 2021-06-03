
package com.kajal.mobile.app.ws.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kajal.mobile.app.ws.service.AddressService;
import com.kajal.mobile.app.ws.service.UserService;
import com.kajal.mobile.app.ws.ui.exception.ErrorMessages;
import com.kajal.mobile.app.ws.ui.exception.UserServiceException;
import com.kajal.mobile.app.ws.ui.model.request.UserDetailRequestModel;
import com.kajal.mobile.app.ws.ui.model.response.AddressesRest;
import com.kajal.mobile.app.ws.ui.model.response.OperationStatusModel;
import com.kajal.mobile.app.ws.ui.model.response.ResultDeleted;
import com.kajal.mobile.app.ws.ui.model.response.SuccessorNot;
import com.kajal.mobile.app.ws.ui.model.response.UserRest;
import com.kajal.mobile.app.ws.ui.shared.dto.AddressDto;
import com.kajal.mobile.app.ws.ui.shared.dto.UserDto;

@RestController
@RequestMapping("users") // http:localhost:8080/users

public class Usercontroller {

	@Autowired
	AddressService addressService;

	@Autowired
	AddressService addressesService;

	@Autowired
	UserService userService;

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getuser(@PathVariable String id) {
		UserRest returnvalue = new UserRest();

		UserDto userDto = userService.getUserByUserid(id);

		BeanUtils.copyProperties(userDto, returnvalue);
		return returnvalue;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest createUser(@RequestBody UserDetailRequestModel userdetail) throws Exception {

		if (userdetail.getFirstname().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessages());

		UserRest returnvalue = new UserRest();

		// UserDto userDto = new UserDto();
		// BeanUtils.copyProperties(userdetail, userDto);
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userdetail, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
		returnvalue = modelMapper.map(createdUser, UserRest.class);

		return returnvalue;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailRequestModel userdetail) {

		UserRest returnvalue = new UserRest();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userdetail, userDto);

		UserDto updateUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updateUser, returnvalue);

		return returnvalue;
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnVaule = new OperationStatusModel();
		returnVaule.setOperationName(ResultDeleted.Delete.name());

		userService.deleteUser(id);

		returnVaule.setOperationResult(SuccessorNot.SUCCESS.name());

		return returnVaule;
	}

	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {

		List<UserRest> returnValue = new ArrayList<>();

		List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();

			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);

		}

		return returnValue;

	}

	// http://localhost:8080/mobile-WS/USERS/ID/ADDRESSES
	@GetMapping(path = "/{id}/addresses", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public CollectionModel<AddressesRest> getUserAddresses(@PathVariable String id) {
		List<AddressesRest> returnvalue = new ArrayList<>();
		List<AddressDto> addressesDto = addressesService.getAddresses(id);

		if (addressesDto != null && !addressesDto.isEmpty()) {

			java.lang.reflect.Type listType = new TypeToken<List<AddressesRest>>() {
			}.getType();
			ModelMapper modelMapper = new ModelMapper();
			returnvalue = modelMapper.map(addressesDto, listType);

		}
		Link userLink = WebMvcLinkBuilder.linkTo(Usercontroller.class).slash(id).withRel("user");

		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Usercontroller.class).getUserAddresses(id))
				.withSelfRel();

		return CollectionModel.of(returnvalue, userLink, selfLink);
	}

	@GetMapping(path = "/{userid}/addresses/{addressId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<AddressesRest> getUserAddress(@PathVariable String userid, @PathVariable String addressId) {

		AddressDto addressesDto = addressesService.getAddress(addressId);
		ModelMapper modelMapper = new ModelMapper();
		AddressesRest returnValue = modelMapper.map(addressesDto, AddressesRest.class);

		// http://localhost:8080/users/<userId>/addresses/{addressId}

		Link userLink = WebMvcLinkBuilder.linkTo(Usercontroller.class).slash(userid).withRel("user");

		Link userAddressLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(Usercontroller.class).getUserAddresses("userid"))
				.withRel("addresses");

		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(Usercontroller.class).getUserAddress(userid, addressId))
				.withSelfRel();

		// returnValue.add(userLink);
		// returnValue.add(userAddressLink);
		// returnValue.add(selfLink);

		return EntityModel.of(returnValue, Arrays.asList(userLink, userAddressLink, selfLink));

	}

	// http://localhost:8080/mobile-app-ws/users/email-verification?token-sdfsdf

	@GetMapping(path = "/email-verification", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {

		OperationStatusModel returnValue = new OperationStatusModel();

		returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());
		boolean isVerified = userService.verifyEmailToken(token);
		if (isVerified) {

			returnValue.setOperationResult(SuccessorNot.SUCCESS.name());

		} else {

			returnValue.setOperationResult(SuccessorNot.ERROR.name());

		}

		return returnValue;
	}

}