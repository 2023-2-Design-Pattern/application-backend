package repository;

import static java.nio.file.StandardCopyOption.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.cau.designpattern.config.HolubSqlConfig;
import com.cau.designpattern.entity.UserEntity;
import com.cau.designpattern.repository.UserRepository;
import com.cau.designpattern.repository.UserRepositoryImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRepositoryTest {

	private String originPath;
	private String testPath;
	private UserRepository userRepository;

	public UserRepositoryTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		originPath = "Dbase_test_origin";
		testPath = "Dbase_test";
		this.userRepository = new UserRepositoryImpl(new HolubSqlConfig(testPath));
	}

	@BeforeEach
	void setUp() throws IOException {
		Files.walk(Paths.get(originPath)).forEach(source -> {
			Path destination = Paths.get(testPath, source.toString().substring(originPath.length()));
			try {
				Files.copy(source, destination, REPLACE_EXISTING);
			} catch (IOException ignored) {
			}
		});
	}

	@Nested
	class GetOneByNameTest {

		@Test
		@DisplayName("회원 조회 성공")
		void userExist() {

			// when
			Optional<UserEntity> actual = userRepository.getOneByName("testUserr");

			// then
			assertThat(actual).isPresent();
			assertThat(actual.get().getName()).isEqualTo("testUserr");
			assertThat(actual.get().getUserId()).isEqualTo(1L);
		}

		@Test
		@DisplayName("해당 이름을 가진 회원 존재하지 않음")
		void userNotExist() {

			// when
			Optional<UserEntity> actual = userRepository.getOneByName("noname");

			// then
			assertThat(actual).isEmpty();
		}
	}

	@Nested
	@DisplayName("회원 추가")
	class InsertTest {

		@Test
		@DisplayName("회원 추가 성공")
		void success() {

			// given
			assertThat(userRepository.getOneByName("newinsert")).isEmpty();

			// when
			userRepository.insert("newinsert");

			// then
			Optional<UserEntity> actual = userRepository.getOneByName("newinsert");
			assertThat(actual).isPresent();
			assertThat(actual.get().getName()).isEqualTo("newinsert");
		}
	}
}