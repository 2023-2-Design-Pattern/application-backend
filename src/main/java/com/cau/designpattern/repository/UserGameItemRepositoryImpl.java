package com.cau.designpattern.repository;

import com.cau.designpattern.config.HolubSqlConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class UserGameItemRepositoryImpl implements UserGameItemRepository {
    private final HolubSqlConfig holubSqlConfig;

    public UserGameItemRepositoryImpl(HolubSqlConfig holubSqlConfig) {
        this.holubSqlConfig = holubSqlConfig;
    }
}
