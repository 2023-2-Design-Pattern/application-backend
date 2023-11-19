package com.cau.designpattern.controller;

import com.cau.designpattern.dto.UserDto;

public interface UserController {

	public UserDto.LoginRes login(UserDto.LoginReq loginReq);
}
