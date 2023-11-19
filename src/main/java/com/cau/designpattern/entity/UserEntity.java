package com.cau.designpattern.entity;

import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserEntity {

	private long userId;

	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserEntity that = (UserEntity)o;
		return userId == that.userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}
}
