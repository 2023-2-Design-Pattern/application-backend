package com.cau.designpattern.integration;

import static java.nio.file.StandardCopyOption.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TestIntegrationTest {

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
	void getByAddrId() throws Exception {

		mockMvc.perform(get("/test/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(content().json(
				"{\n"
					+ "    \"addrId\": 1,\n"
					+ "    \"street\": \"34 Quarry Ln.\",\n"
					+ "    \"city\": \"Bedrock\",\n"
					+ "    \"state\": \"AZ\",\n"
					+ "    \"zip\": \"00000\"\n"
					+ "}"
			))
		;
	}
}