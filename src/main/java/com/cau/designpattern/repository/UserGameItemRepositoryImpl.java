package com.cau.designpattern.repository;

import com.cau.designpattern.config.HolubSqlConfig;
import com.cau.designpattern.entity.UserGameItemEntity;
import com.holub.database.jdbc.JDBCConnection;
import com.holub.database.jdbc.JDBCStatement;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM userGameItem WHERE userGameId = ? ORDER BY userGameItemId ASC)");
            pstmt.setLong(1, userGameId);
            ResultSet rs = pstmt.executeQuery();
//            ResultSet rs = stmt.executeQuery(
//                    String.format("SELECT * FROM userGameItem WHERE userGameId = %d ORDER BY userGameItemId ASC)", userGameId));

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
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM userGameItem WHERE userGameId = ? ORDER BY userGameItemId ASC)");
            pstmt.setLong(1, userGameId);
            ResultSet rs = pstmt.executeQuery();
//            ResultSet rs = stmt.executeQuery(
//                    String.format("SELECT * FROM userGameItem WHERE userGameId = %d ORDER BY userGameItemId ASC)", userGameId));

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
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void useItem(long userGameItemId, long userGameId) {
        try {
            JDBCConnection connection = holubSqlConfig.getConnection();

            JDBCStatement stmt = (JDBCStatement)connection.createStatement();
            stmt.executeUpdate(
                    String.format("DELETE FROM userGameItem WHERE userGameItemId = %d AND userGameId = %d",
                            userGameItemId, userGameId)
            );
            stmt.close();

            connection.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void rushItem(long userGameItemId1, long userGameItemId2, long userGameId) {
        try {
            if (userGameItemId1 == userGameItemId2) {
                throw new RuntimeException();
            }

            JDBCConnection connection = holubSqlConfig.getConnection();

            JDBCStatement stmt = (JDBCStatement)connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    String.format("SELECT * FROM userGameItem WHERE userGameId = %d)", userGameId));

            int check = 2;
            long itemId1 = 0;
            long itemId2 = 0;
            while(rs.next()) {
                long userGameItemId = rs.getLong("userGameItemId");
                if (userGameItemId == userGameItemId1) {
                    itemId1 = rs.getLong("itemId");
                    check--;
                } else if (userGameItemId == userGameItemId2) {
                    itemId2 = rs.getLong("itemId");
                    check--;
                }
            }
            if (check != 0 || itemId1 != itemId2) {
                throw new RuntimeException();
            }

            long rushItemId = itemId1 + 3;

            stmt.executeUpdate(
                    String.format("DELETE FROM userGameItem WHERE userGameItemId = %d OR userGameItemId = %d",
                            userGameItemId1, userGameItemId2)
            );
            stmt.close();

            connection.close();

            getItem(userGameId, rushItemId);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
