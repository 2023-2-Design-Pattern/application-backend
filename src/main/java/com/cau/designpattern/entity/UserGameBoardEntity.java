package com.cau.designpattern.entity;

import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserGameBoardEntity {

	private long userGameBoardId;

	private String data;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserGameBoardEntity that = (UserGameBoardEntity)o;
		return userGameBoardId == that.userGameBoardId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userGameBoardId);
	}
}
