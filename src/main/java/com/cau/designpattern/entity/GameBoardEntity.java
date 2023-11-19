package com.cau.designpattern.entity;

import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameBoardEntity {

	private long gameBoardId;

	private String data;

	private String itemData;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GameBoardEntity that = (GameBoardEntity)o;
		return gameBoardId == that.gameBoardId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(gameBoardId);
	}
}
