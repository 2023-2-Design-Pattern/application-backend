package com.cau.designpattern.service;

import com.cau.designpattern.dto.GameDto;

public interface GameService {

	public GameDto.StartRes start(String name, long round);

	public void save(GameDto.SaveReq saveReq);

	public void finish(GameDto.FinishReq finishReq);
}
