package com.cau.designpattern.entity;

import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserGameEntity {

	private long userGameId;

	private long userId;

	private long gameBoardId;

	private long userGameBoardId;

	private int health;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserGameEntity that = (UserGameEntity)o;
		return userGameId == that.userGameId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userGameId);
	}
}
