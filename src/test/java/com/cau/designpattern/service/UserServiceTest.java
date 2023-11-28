package com.cau.designpattern.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.cau.designpattern.dto.UserDto;
import com.cau.designpattern.entity.UserEntity;
import com.cau.designpattern.repository.UserGameRepository;
import com.cau.designpattern.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	private UserRepository userRepository;

	private UserGameRepository userGameRepository;

	public UserServiceTest() {
		userRepository = Mockito.mock(UserRepository.class);
		userGameRepository = Mockito.mock(UserGameRepository.class);
		this.userService = new UserServiceImpl(userRepository, userGameRepository);
	}

	@Nested
	class LoginTest {

		@Test
		@DisplayName("로그인 성공")
		void success() {

			// given
			UserEntity user = UserEntity.builder().userId(9L).build();
			willReturn(Optional.of(user)).given(userRepository).getOneByName(null);

			willReturn(3L).given(userGameRepository).getLastGameBoardId(9L);

			// when
			UserDto.LoginRes actual = userService.login(new UserDto.LoginReq());

			// then
			assertThat(actual.getRound()).isEqualTo(3L);
		}
	}
}