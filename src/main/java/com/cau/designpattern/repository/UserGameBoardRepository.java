package com.cau.designpattern.repository;

import com.cau.designpattern.entity.GameBoardEntity;
import com.cau.designpattern.entity.UserGameBoardEntity;

public interface UserGameBoardRepository {

	public UserGameBoardEntity getById(long userGameBoardId);

	public long insert(long gameBoardId);

	public void update(long userGameBoardId, String data);
}
