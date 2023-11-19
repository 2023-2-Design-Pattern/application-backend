package com.cau.designpattern.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class GameDto {

	@Getter
	@ToString
	public static class StartReq {
		private String name;
		private long round;
	}

	@Getter
	@Builder
	@ToString
	public static class StartRes {
		private String gameBoard;
		private int health;
	}

	@Getter
	@ToString
	public static class SaveReq {
		private String name;
		private long round;
		private String gameBoard;
		private int health;
	}

	@Getter
	@ToString
	public static class FinishReq {
		private String name;
		private long round;
		private boolean clear;
	}
}
