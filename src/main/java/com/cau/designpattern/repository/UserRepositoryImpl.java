package com.cau.designpattern.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cau.designpattern.config.HolubSqlConfig;
import com.cau.designpattern.entity.UserEntity;
import com.cau.designpattern.util.JDBCUtil;
import com.holub.database.jdbc.JDBCConnection;
import com.holub.database.jdbc.JDBCStatement;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private final HolubSqlConfig holubSqlConfig;

	public UserRepositoryImpl(HolubSqlConfig holubSqlConfig) {
		this.holubSqlConfig = holubSqlConfig;
	}

	public void insert(String name) {
		try {
			JDBCConnection connection = holubSqlConfig.getConnection();

			JDBCStatement stmt = (JDBCStatement)connection.createStatement();

			long userId = JDBCUtil.generateId(stmt, "user");

			stmt = (JDBCStatement)connection.createStatement();
			stmt.executeUpdate(String.format("INSERT INTO user VALUES (%d, '%s')", userId, name));
			stmt.close();

			connection.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public Optional<UserEntity> getOneByName(String name) {

		try {
			JDBCConnection connection = holubSqlConfig.getConnection();
//			JDBCStatement stmt = (JDBCStatement)connection.createStatement();
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user WHERE name = ?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
//			ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM user WHERE name = '%s'", name));
			rs.next();
			UserEntity user = UserEntity.builder()
				.userId(rs.getLong("userId"))
				.name(rs.getString("name"))
				.build();
//			stmt.close();
			pstmt.close();

			connection.close();

			return Optional.of(user);
		} catch (NullPointerException e) { // 게임 한판도 안한 유저
			return Optional.empty();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}
