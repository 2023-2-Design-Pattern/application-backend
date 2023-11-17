package com.cau.designpattern.controller;

import org.springframework.web.bind.annotation.PathVariable;

import com.cau.designpattern.entity.AddressEntity;

public interface TestController {

	public String test();

	public AddressEntity getByAddrId(@PathVariable long addrId);
}
