package repository;

import static java.nio.file.StandardCopyOption.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.cau.designpattern.config.HolubSqlConfig;
import com.cau.designpattern.entity.GameBoardEntity;
import com.cau.designpattern.repository.GameBoardRepository;
import com.cau.designpattern.repository.GameBoardRepositoryImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameBoardRepositoryTest {

	private String originPath;
	private String testPath;
	private GameBoardRepository gameBoardRepository;

	public GameBoardRepositoryTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		originPath = "Dbase_test_origin";
		testPath = "Dbase_test";
		this.gameBoardRepository = new GameBoardRepositoryImpl(new HolubSqlConfig(testPath));
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
	class GetByIdTest {

		@Test
		@DisplayName("게임판 조회 성공")
		void userExist() {

			// when
			GameBoardEntity actual = gameBoardRepository.getById(2L);

			// then
			assertThat(actual.getGameBoardId()).isEqualTo(2L);
			assertThat(actual.getData()).isEqualTo(
				"W1s5OSwzMjEsNDIzNDIzLDU0NSwxNjU0M10sWzQyLDQzMjEsMzQ1NiwxXSxbMjMyM10sWzMsNSwxMCw0NTQzXV0=");
		}

		@Test
		@DisplayName("게임판 존재하지 않음")
		void userNotExist() {
			assertThatThrownBy(() -> gameBoardRepository.getById(9L)).isInstanceOf(RuntimeException.class);
		}
	}
}