package com.cau.designpattern.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cau.designpattern.dto.GameDto;
import com.cau.designpattern.service.GameService;

@RestController
public class GameControllerImpl implements GameController {

	private final GameService gameService;

	public GameControllerImpl(GameService gameService) {
		this.gameService = gameService;
	}

	@Override
	@PostMapping("/games/save")
	public void save(@RequestBody GameDto.SaveReq saveReq) {
		gameService.save(saveReq);
	}

	@Override
	@PostMapping("/games/finish")
	public void finish(@RequestBody GameDto.FinishReq finishReq) {
		gameService.finish(finishReq);
	}

	@Override
	@GetMapping("/games/{name}/{round}")
	public GameDto.StartRes start(@PathVariable String name, @PathVariable long round) {
		return gameService.start(name, round);
	}
}
