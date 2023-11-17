package com.cau.designpattern.service;

import org.springframework.stereotype.Service;

import com.cau.designpattern.entity.AddressEntity;
import com.cau.designpattern.repository.TestRepository;

@Service
public class TestServiceImpl implements TestService {

	private final TestRepository testRepository;

	public TestServiceImpl(TestRepository testRepository) {
		this.testRepository = testRepository;
	}

	public void test() {
		testRepository.test();
		System.out.println("test");
	}

	public AddressEntity getByAddrId(long addrId) {
		return testRepository.getOneByAddrId(addrId);
	}
}
