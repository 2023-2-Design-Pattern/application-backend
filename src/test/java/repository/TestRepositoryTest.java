package repository;

import static java.nio.file.StandardCopyOption.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.cau.designpattern.config.HolubSqlConfig;
import com.cau.designpattern.entity.AddressEntity;
import com.cau.designpattern.repository.TestRepository;
import com.cau.designpattern.repository.TestRepositoryImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestRepositoryTest {

	private String originPath;
	private String testPath;
	private TestRepository testRepository;

	public TestRepositoryTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		originPath = "Dbase_test_origin";
		testPath = "Dbase_test";
		this.testRepository = new TestRepositoryImpl(new HolubSqlConfig(testPath));
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
	class FindAllTest {

		@Test
		void success() {

			// when
			AddressEntity actual = testRepository.getOneByAddrId(1L);

			// then
			assertThat(actual.getCity()).isEqualTo("Bedrock");
		}
	}
}