package com.cau.designpattern.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cau.designpattern.dto.UserDto;
import com.cau.designpattern.service.UserService;

@RestController
public class UserControllerImpl implements UserController {

	private final UserService userService;

	public UserControllerImpl(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public UserDto.LoginRes login(@RequestBody UserDto.LoginReq loginReq) {
		return userService.login(loginReq);
	}
}
