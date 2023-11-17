package com.cau.designpattern.entity;

import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressEntity {

	private long addrId;

	private String street;

	private String city;

	private String state;

	private String zip;

	public AddressEntity(long addrId, String street, String city, String state, String zip) {
		this.addrId = addrId;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AddressEntity that = (AddressEntity)o;
		return addrId == that.addrId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(addrId);
	}
}
