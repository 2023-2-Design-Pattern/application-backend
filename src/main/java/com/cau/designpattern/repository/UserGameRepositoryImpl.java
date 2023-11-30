package com.cau.designpattern.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cau.designpattern.config.HolubSqlConfig;
import com.cau.designpattern.entity.UserGameEntity;
import com.cau.designpattern.util.JDBCUtil;
import com.holub.database.jdbc.JDBCConnection;
import com.holub.database.jdbc.JDBCStatement;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class UserGameRepositoryImpl implements UserGameRepository {

	private final HolubSqlConfig holubSqlConfig;

	public UserGameRepositoryImpl(HolubSqlConfig holubSqlConfig) {
		this.holubSqlConfig = holubSqlConfig;
	}

	/**
	 * 가장 높은 게임보드ID(게임레벨)를 가져옵니다
	 * @param userId 회원ID
	 * @return 가장 높은 게임보드ID(플레이 기록이 없다면 1, 1단계 플레이 중이라면 1, 1단계 클리어했다면 2, 3단계까지 클리어했다면 4)
	 */
	@Override
	public long getLastGameBoardId(long userId) {

		try {
			JDBCConnection connection = holubSqlConfig.getConnection();

			JDBCStatement stmt = (JDBCStatement)connection.createStatement();
			ResultSet rs = stmt.executeQuery(
				String.format("SELECT * FROM userGame WHERE userId = %d ORDER BY gameBoardId DESC, status DESC",
					userId));
			rs.next();
			long lastGameBoardId = rs.getLong("gameBoardId");
			stmt.close();

			connection.close();

			boolean isClear = false;
			do {
				if (rs.getLong("gameBoardId") != lastGameBoardId)
					break;
				if (rs.getInt("status") != 2)
					continue;
				isClear = true;
				break;
			} while (rs.next());

			return lastGameBoardId + (isClear ? 1 : 0);

		} catch (SQLException | NullPointerException e) { // 게임 한판도 안한 유저
			return 1;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * userId를 가진 회원이 플레이 중인 라운드(gameBoardId) 판을 불러옵니다
	 * @param userId
	 * @param gameBoardId
	 * @return 해당 판이 없다면 Optional.empty();
	 */
	@Override
	public Optional<UserGameEntity> findByUserIdAndGameBoardId(long userId, long gameBoardId) {

		try {
			JDBCConnection connection = holubSqlConfig.getConnection();

			JDBCStatement stmt = (JDBCStatement)connection.createStatement();
			ResultSet rs = stmt.executeQuery(
				String.format("SELECT * FROM userGame WHERE userId = %d AND gameBoardId = %d AND status = 0)", userId,
					gameBoardId));
			rs.next();
			UserGameEntity userGame = UserGameEntity.builder()
				.userGameId(rs.getLong("userGameId"))
				.userId(rs.getLong("userId"))
				.gameBoardId(rs.getLong("gameBoardId"))
				.userGameBoardId(rs.getLong("userGameBoardId"))
				.health(rs.getInt("health"))
				.build();
			stmt.close();

			connection.close();

			return Optional.of(userGame);
		} catch (NullPointerException e) {    // SQL 결과 없음
			return Optional.empty();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void insert(long userId, long gameBoardId, long userGameBoardId) {

		try {
			JDBCConnection connection = holubSqlConfig.getConnection();

			JDBCStatement stmt = (JDBCStatement)connection.createStatement();

			long userGameId = JDBCUtil.generateId(stmt, "userGame");

			stmt = (JDBCStatement)connection.createStatement();
			stmt.executeUpdate(
				String.format("INSERT INTO userGame VALUES (%d, %d, %d, %d, 0, 100)", userGameId, userId, gameBoardId,
					userGameBoardId));
			stmt.close();

			connection.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 체력 업데이트
	 */
	@Override
	public void updateHealth(long userGameId, int health) {

		try {
			JDBCConnection connection = holubSqlConfig.getConnection();

			JDBCStatement stmt = (JDBCStatement)connection.createStatement();
			stmt.executeUpdate(
				String.format("UPDATE userGame SET health = %d WHERE userGameId = %d", health, userGameId));
			stmt.close();

			connection.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 게임 상태(status) 업데이트(0:플레이 중 / 1:실패 / 2:클리어)
	 */
	@Override
	public void updateStatus(long userGameId, int status) {

		try {
			JDBCConnection connection = holubSqlConfig.getConnection();

			JDBCStatement stmt = (JDBCStatement)connection.createStatement();
			stmt.executeUpdate(
				String.format("UPDATE userGame SET status = %d WHERE userGameId = %d", status, userGameId));
			stmt.close();

			connection.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
