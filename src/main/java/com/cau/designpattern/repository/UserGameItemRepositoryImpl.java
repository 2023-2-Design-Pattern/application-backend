package com.cau.designpattern.repository;

import com.cau.designpattern.config.HolubSqlConfig;
import com.cau.designpattern.entity.UserGameItemEntity;
import com.holub.database.jdbc.JDBCConnection;
import com.holub.database.jdbc.JDBCStatement;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Repository
public class UserGameItemRepositoryImpl implements UserGameItemRepository {
    private final HolubSqlConfig holubSqlConfig;

    public UserGameItemRepositoryImpl(HolubSqlConfig holubSqlConfig) {
        this.holubSqlConfig = holubSqlConfig;
    }

    @Override
    public List<UserGameItemEntity> getAllUserGameItems(long userGameId) {
        List<UserGameItemEntity> list = new ArrayList<>();

        try {
            JDBCConnection connection = holubSqlConfig.getConnection();

            JDBCStatement stmt = (JDBCStatement)connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    String.format("SELECT * FROM userGameItem WHERE userGameId = %d ORDER BY userGameItemId ASC)", userGameId));

            while(rs.next()) {
                UserGameItemEntity userGameItemEntity = UserGameItemEntity.builder()
                        .userGameItemId(rs.getLong("userGameItemId"))
                        .userGameId(rs.getLong("userGameId"))
                        .itemId(rs.getLong("itemId"))
                        .build();
                list.add(userGameItemEntity);
            }
            stmt.close();

            connection.close();

            return list;
        } catch (NullPointerException e) {
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void getItem(long userGameId, long itemId) {
        try {
            JDBCConnection connection = holubSqlConfig.getConnection();

            JDBCStatement stmt = (JDBCStatement)connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    String.format("SELECT * FROM userGameItem WHERE userGameId = %d ORDER BY userGameItemId ASC)", userGameId));

            long userGameItemId = 1;
            while(rs.next()) {
                if (userGameItemId < rs.getLong("userGameItemId")) {
                    break;
                }
                userGameItemId++;
            }
            stmt.executeUpdate(
                    String.format("INSERT INTO userGameItem VALUES (%d, %d, %d)", userGameItemId, userGameId, itemId)
            );
            stmt.close();

            connection.close();
        } catch (NullPointerException e) {

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}