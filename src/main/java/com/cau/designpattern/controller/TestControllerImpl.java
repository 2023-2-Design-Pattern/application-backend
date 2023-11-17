package com.cau.designpattern.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cau.designpattern.entity.AddressEntity;
import com.cau.designpattern.service.TestService;

@RestController
public class TestControllerImpl implements TestController {

	private final TestService testService;

	public TestControllerImpl(TestService testService) {
		this.testService = testService;
	}

	@GetMapping("/test")
	public String test() {
		testService.test();
		return "test";
	}

	@GetMapping("/test/{addrId}")
	public AddressEntity getByAddrId(@PathVariable long addrId) {
		return testService.getByAddrId(addrId);
	}
}
