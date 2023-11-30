package com.cau.designpattern.repository;

import java.util.Optional;

import com.cau.designpattern.entity.UserEntity;

public interface UserRepository {

	public Optional<UserEntity> getOneByName(String name);

	public void insert(String name);
}
