package com.cau.designpattern.repository;

import com.cau.designpattern.entity.GameBoardEntity;

public interface GameBoardRepository {

	public GameBoardEntity getById(long gameBoardId);
}
