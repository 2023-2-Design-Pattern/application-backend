package com.cau.designpattern.repository;

import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.cau.designpattern.config.HolubSqlConfig;
import com.cau.designpattern.entity.AddressEntity;
import com.holub.database.jdbc.JDBCConnection;
import com.holub.database.jdbc.JDBCStatement;

@Repository
public class TestRepositoryImpl implements TestRepository {

	private final HolubSqlConfig holubSqlConfig;

	public TestRepositoryImpl(HolubSqlConfig holubSqlConfig) {
		this.holubSqlConfig = holubSqlConfig;
	}

	public void test() {
		try {
			JDBCConnection connection = holubSqlConfig.getConnection();
			JDBCStatement stmt = (JDBCStatement)connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from address where addrId = 1");

			while (rs.next()) {
				String name = rs.getString("zip");
				JDBCStatement stmt2 = (JDBCStatement)connection.createStatement();

				stmt2.executeUpdate("update address set zip='33333' where addrId = " + rs.getString("addrId"));

				JDBCStatement stmt3 = (JDBCStatement)connection.createStatement();
				ResultSet rs3 = stmt3.executeQuery("Select * from address");
				rs3.next();
			}

			connection.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public AddressEntity getOneByAddrId(long addrId) {

		try {
			JDBCConnection connection = holubSqlConfig.getConnection();
			JDBCStatement stmt = (JDBCStatement)connection.createStatement();
			ResultSet rs = stmt.executeQuery(
				"SELECT * FROM address WHERE addrId = " + addrId); // sql 인젝션 위험 - PreparedStatement로 바꾸면 짱일듯
			rs.next();
			AddressEntity address = AddressEntity.builder()
				.addrId(rs.getLong("addrId"))
				.street(rs.getString("street"))
				.city(rs.getString("city"))
				.state(rs.getString("state"))
				.zip(rs.getString("zip"))
				.build();

			// AddressEntity address1 = new AddressEntity(123, "dkkd","ddd");

			connection.close();

			return address;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
