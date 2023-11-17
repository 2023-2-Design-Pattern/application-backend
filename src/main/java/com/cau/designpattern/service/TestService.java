package com.cau.designpattern.service;

import com.cau.designpattern.entity.AddressEntity;

public interface TestService {
	public void test();

	public AddressEntity getByAddrId(long addrId);
}
