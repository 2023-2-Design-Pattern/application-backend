package com.cau.designpattern.service;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.cau.designpattern.dto.GameDto;
import com.cau.designpattern.entity.UserGameEntity;
import com.cau.designpattern.repository.UserGameBoardRepository;
import com.cau.designpattern.repository.UserGameRepository;

@Service
public class GameServiceImpl implements GameService {

	private final UserService userService;
	private final UserGameRepository userGameRepository;
	private final UserGameBoardRepository userGameBoardRepository;

	public GameServiceImpl(UserService userService, UserGameRepository userGameRepository,
		UserGameBoardRepository userGameBoardRepository) {
		this.userService = userService;
		this.userGameRepository = userGameRepository;
		this.userGameBoardRepository = userGameBoardRepository;
	}

	/**
	 * 게임을 시작합니다.
	 * 이전에 플레이 한 기록이 있다면 데이터를 불러옵니다.
	 * @param name 회원명
	 * @param round 플레이할 라운드
	 * @return 게임판과 체력
	 */
	@Override
	public GameDto.StartRes start(String name, long round) {

		long userId = userService.getUserIdByName(name);
		UserGameEntity userGame = userGameRepository.findByUserIdAndGameBoardId(userId, round)
			.orElseGet(() -> { // 이전에 플레이한 기록이 없으면 userGameBoard 생성 -> userGame 생성
				long userGameBoardId = userGameBoardRepository.insert(round); // userGameBoard 생성
				userGameRepository.insert(userId, round, userGameBoardId); // userGame 생성
				return userGameRepository.findByUserIdAndGameBoardId(userId, round).get();
			});

		String userGameBoard = userGameBoardRepository.getById(userGame.getUserGameBoardId()).getData();

		return GameDto.StartRes.builder()
			.gameBoard(new String(Base64.getUrlDecoder().decode(userGameBoard))) // Base64로 인코딩된 게임판을 디코딩하여 반환
			.health(userGame.getHealth())
			.build();
	}

	/**
	 * 플레이 중인 게임을 저장합니다.
	 * @param saveReq 플레이 중이던 게임 상태
	 */
	@Override
	public void save(GameDto.SaveReq saveReq) {

		long userId = userService.getUserIdByName(saveReq.getName());
		UserGameEntity userGame = userGameRepository.findByUserIdAndGameBoardId(userId, saveReq.getRound()).get();
		// 콤마(,)가 포함된 게임판 데이터의 경우 csv 파싱이 불가능하므로, 게임판을 Base64로 인코딩하여 저장
		String encodedData = Base64.getUrlEncoder().encodeToString(saveReq.getGameBoard().getBytes());
		userGameBoardRepository.update(userGame.getUserGameBoardId(), encodedData);

		userGameRepository.updateHealth(userGame.getUserGameId(), saveReq.getHealth()); // 체력 업데이트
	}

	/**
	 * 게임을 종료합니다.
	 * @param finishReq 종료할 게임
	 */
	@Override
	public void finish(GameDto.FinishReq finishReq) {

		long userId = userService.getUserIdByName(finishReq.getName());
		UserGameEntity userGame = userGameRepository.findByUserIdAndGameBoardId(userId, finishReq.getRound()).get();
		// 게임 상태 업데이트(1:실패 / 2:클리어)
		userGameRepository.updateStatus(userGame.getUserGameId(), finishReq.isClear() ? 2 : 1);
	}
}
