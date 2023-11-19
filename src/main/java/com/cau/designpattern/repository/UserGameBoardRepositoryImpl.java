package com.cau.designpattern.repository;

import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.cau.designpattern.config.HolubSqlConfig;
import com.cau.designpattern.entity.UserGameBoardEntity;
import com.cau.designpattern.util.JDBCUtil;
import com.holub.database.jdbc.JDBCConnection;
import com.holub.database.jdbc.JDBCStatement;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class UserGameBoardRepositoryImpl implements UserGameBoardRepository {

	private final HolubSqlConfig holubSqlConfig;
	private final GameBoardRepository gameBoardRepository;

	public UserGameBoardRepositoryImpl(HolubSqlConfig holubSqlConfig,
		GameBoardRepository gameBoardRepository) {
		this.holubSqlConfig = holubSqlConfig;
		this.gameBoardRepository = gameBoardRepository;
	}

	@Override
	public UserGameBoardEntity getById(long userGameBoardId) {

		try {
			JDBCConnection connection = holubSqlConfig.getConnection();

			JDBCStatement stmt = (JDBCStatement)connection.createStatement();
			ResultSet rs = stmt.executeQuery(
				String.format("SELECT * FROM userGameBoard WHERE userGameBoardId = %d", userGameBoardId));
			rs.next();
			UserGameBoardEntity userGameBoard = UserGameBoardEntity.builder()
				.userGameBoardId(rs.getLong("userGameBoardId"))
				.data(rs.getString("data"))
				.build();
			stmt.close();

			connection.close();

			return userGameBoard;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public long insert(long gameBoardId) {

		try {
			JDBCConnection connection = holubSqlConfig.getConnection();

			JDBCStatement stmt = (JDBCStatement)connection.createStatement();

			long userGameBoardId = JDBCUtil.generateId(stmt, "userGameBoard");

			stmt = (JDBCStatement)connection.createStatement();
			String data = gameBoardRepository.getById(gameBoardId).getData();
			stmt.executeUpdate(String.format("INSERT INTO userGameBoard VALUES (%d, '%s')", userGameBoardId, data));
			stmt.close();

			connection.close();

			return userGameBoardId;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void update(long userGameBoardId, String data) {

		try {
			JDBCConnection connection = holubSqlConfig.getConnection();
			JDBCStatement stmt = (JDBCStatement)connection.createStatement();
			stmt.executeUpdate(
				String.format("UPDATE userGameBoard SET data = '%s' WHERE userGameBoardId = %d", data, userGameBoardId));
			stmt.close();

			connection.close();

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
