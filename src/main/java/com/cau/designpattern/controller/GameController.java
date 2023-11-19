package com.cau.designpattern.controller;

import com.cau.designpattern.dto.GameDto;

public interface GameController {

	public GameDto.StartRes start(String name, long round);

	public void save(GameDto.SaveReq saveReq);

	public void finish(GameDto.FinishReq finishReq);
}
