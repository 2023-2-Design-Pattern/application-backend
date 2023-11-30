package com.cau.designpattern.service;

import com.cau.designpattern.dto.UserDto;

public interface UserService {

	public UserDto.LoginRes login(UserDto.LoginReq loginReq);

	public long getUserIdByName(String name);
}
