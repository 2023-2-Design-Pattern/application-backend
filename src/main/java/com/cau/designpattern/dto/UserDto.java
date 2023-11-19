package com.cau.designpattern.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class UserDto {

	@Getter
	@ToString
	public static class LoginReq {
		private String name;
	}

	@Getter
	@Builder
	@ToString
	public static class LoginRes {
		private long round;
	}
}
