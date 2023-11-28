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
import com.cau.designpattern.entity.UserGameEntity;
import com.cau.designpattern.repository.UserGameRepository;
import com.cau.designpattern.repository.UserGameRepositoryImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserGameRepositoryTest {

	private String originPath;
	private String testPath;
	private UserGameRepository userGameRepository;

	public UserGameRepositoryTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		originPath = "Dbase_test_origin";
		testPath = "Dbase_test";
		this.userGameRepository = new UserGameRepositoryImpl(new HolubSqlConfig(testPath));
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
	class GetLastGameBoardTest {

		@Test
		@DisplayName("플레이할 라운드 조회 성공")
		void success_whilePlaying() {
			assertThat(userGameRepository.getLastGameBoardId(0L)).isEqualTo(4L);
		}
	}

	@Nested
	class FindByUserIdAndGameBoardIdTest {

		@Test
		@DisplayName("플레이할 라운드 조회 성공")
		void gameExist() {

			// when
			Optional<UserGameEntity> actual = userGameRepository.findByUserIdAndGameBoardId(0L, 2L);

			// then
			assertThat(actual).isPresent();
			assertThat(actual.get().getUserId()).isEqualTo(0L);
			assertThat(actual.get().getUserGameBoardId()).isEqualTo(7L);
			assertThat(actual.get().getGameBoardId()).isEqualTo(2L);
			assertThat(actual.get().getHealth()).isEqualTo(100);
		}

		@Test
		@DisplayName("조회 실패")
		void gameNotExist() {

			// when
			Optional<UserGameEntity> actual = userGameRepository.findByUserIdAndGameBoardId(9L, 4L);

			// then
			assertThat(actual).isEmpty();
		}
	}
}