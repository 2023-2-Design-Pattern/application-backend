package com.cau.designpattern.integration;

import static java.nio.file.StandardCopyOption.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GameIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Value("${db.origin-path}")
	private String originPath;

	@Value("${db.path}")
	private String testPath;

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

	@Test
	@DisplayName("게임 시작")
	void startTest() throws Exception {

		mockMvc.perform(get("/games/kmss/2")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(
				"{\"gameBoard\":\"[[99,321,423423,545,16543],[42,4321,3456,1],[2323],[3,5,10,4543]]\",\"health\":100}"));
	}

	@Test
	@DisplayName("게임 저장")
	void saveTest() throws Exception {

		mockMvc.perform(post("/games/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n"
					+ "    \"name\":\"kmss\",\n"
					+ "    \"round\":2,\n"
					+ "    \"health\":50,\n"
					+ "    \"gameBoard\":\"[0,1,2,3]\"\n"
					+ "}"))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게임 종료")
	void finishTest() throws Exception {

		mockMvc.perform(post("/games/finish")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n"
					+ "    \"name\":\"kmss\",\n"
					+ "    \"round\":2,\n"
					+ "    \"clear\":true\n"
					+ "}"))
			.andExpect(status().isOk());
	}
}