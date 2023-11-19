package com.cau.designpattern.repository;

import java.util.Optional;

import com.cau.designpattern.entity.UserGameEntity;

public interface UserGameRepository {

	public long getLastGameBoardId(long userId);

	public Optional<UserGameEntity> findByUserIdAndGameBoardId(long userId, long gameBoardId);

	public void insert(long userId, long gameBoardId, long userGameBoardId);

	public void updateHealth(long userGameId, int health);

	public void updateStatus(long userGameId, int status);
}
