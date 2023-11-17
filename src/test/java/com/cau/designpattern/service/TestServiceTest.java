package com.cau.designpattern.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.cau.designpattern.entity.AddressEntity;
import com.cau.designpattern.repository.TestRepository;
import com.cau.designpattern.repository.TestRepositoryImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TestServiceTest {

	@InjectMocks
	private TestService testService;

	private TestRepository testRepository;

	public TestServiceTest() {
		testRepository = Mockito.mock(TestRepositoryImpl.class);
		this.testService = new TestServiceImpl(testRepository);
	}

	@Nested
	class GetAllTest {

		@Test
		void success() {

			// given
			AddressEntity expected = AddressEntity.builder().addrId(3L).build();
			willReturn(expected).given(testRepository).getOneByAddrId(anyLong());

			// when
			AddressEntity actual = testService.getByAddrId(3L);

			// then
			assertThat(actual).isEqualTo(expected);
		}
	}
}