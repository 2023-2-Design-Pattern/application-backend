package com.cau.designpattern.service;

import org.springframework.stereotype.Service;

import com.cau.designpattern.dto.UserDto;
import com.cau.designpattern.entity.UserEntity;
import com.cau.designpattern.repository.UserGameRepository;
import com.cau.designpattern.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserGameRepository userGameRepository;

	public UserServiceImpl(UserRepository userRepository, UserGameRepository userGameRepository) {
		this.userRepository = userRepository;
		this.userGameRepository = userGameRepository;
	}

	@Override
	public UserDto.LoginRes login(UserDto.LoginReq loginReq) {

		UserEntity user = userRepository.getOneByName(loginReq.getName()).orElseGet(() -> {
			userRepository.insert(loginReq.getName()); // 회원 신규 생성
			return userRepository.getOneByName(loginReq.getName()).get();
		});

		// 최근 플레이 중인, 혹은 플레이할 수 있는 라운드 반환
		long lastGameBoardId = userGameRepository.getLastGameBoardId(user.getUserId());

		return UserDto.LoginRes.builder().round(lastGameBoardId).build();
	}

	@Override
	public long getUserIdByName(String name) {
		return userRepository.getOneByName(name).get().getUserId();
	}
}
