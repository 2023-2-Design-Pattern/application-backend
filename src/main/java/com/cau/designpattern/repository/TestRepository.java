package com.cau.designpattern.repository;

import com.cau.designpattern.entity.AddressEntity;

public interface TestRepository {
	public void test();

	public AddressEntity getOneByAddrId(long addrId);
}
