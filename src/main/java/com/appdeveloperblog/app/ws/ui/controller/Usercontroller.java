
package com.appdeveloperblog.app.ws.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdeveloperblog.app.ws.service.UserService;
import com.appdeveloperblog.app.ws.ui.model.request.UserDetailRequestModel;

import com.appdeveloperblog.app.ws.ui.model.response.UserRest;
import com.appdeveloperblog.app.ws.ui.shared.dto.UserDto;

@RestController
@RequestMapping("users") // http:localhost:8080/users

public class Usercontroller {

	@Autowired
	
	UserService userService;

	@GetMapping(path="/{id}")

	public UserRest getuser(@PathVariable String id) {
		UserRest returnvalue = new UserRest();

		UserDto userDto = userService.findUserByUserid(id);
		
		BeanUtils.copyProperties(userDto,returnvalue);
		return returnvalue;
	}

	@PostMapping

	public UserRest createUser(@RequestBody UserDetailRequestModel userdetail) {

		UserRest returnvalue = new UserRest();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userdetail, userDto);

		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnvalue);

		return returnvalue;
	}

	@PutMapping
	public String updateUser() {

		return "update user was called";
	}

	@DeleteMapping
	public String deleteUser() {

		return "delete  user was called";
	}

}
