package com.cau.designpattern.repository;

import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.cau.designpattern.config.HolubSqlConfig;
import com.cau.designpattern.entity.GameBoardEntity;
import com.holub.database.jdbc.JDBCConnection;
import com.holub.database.jdbc.JDBCStatement;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class GameBoardRepositoryImpl implements GameBoardRepository {

	private final HolubSqlConfig holubSqlConfig;

	public GameBoardRepositoryImpl(HolubSqlConfig holubSqlConfig) {
		this.holubSqlConfig = holubSqlConfig;
	}

	@Override
	public GameBoardEntity getById(long gameBoardId) {
		try {
			JDBCConnection connection = holubSqlConfig.getConnection();

			JDBCStatement stmt = (JDBCStatement)connection.createStatement();
			ResultSet rs = stmt.executeQuery(
				String.format("SELECT * FROM gameBoard WHERE gameBoardId = %d", gameBoardId));
			rs.next();
			GameBoardEntity gameBoard = GameBoardEntity.builder()
				.gameBoardId(rs.getLong("gameBoardId"))
				.data(rs.getString("data"))
				.build();
			stmt.close();

			connection.close();

			return gameBoard;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
