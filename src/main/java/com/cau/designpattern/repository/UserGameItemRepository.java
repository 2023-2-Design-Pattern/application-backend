package com.cau.designpattern.repository;

import com.cau.designpattern.entity.UserGameItemEntity;

import java.util.List;

public interface UserGameItemRepository {
    public List<UserGameItemEntity> getAllUserGameItems(long userGameId);
    public void getItem(long userGameId, long itemId);
}
