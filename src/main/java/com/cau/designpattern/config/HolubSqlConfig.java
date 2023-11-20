package com.cau.designpattern.config;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.holub.database.jdbc.JDBCConnection;


// TODO: 어노테이션을 싱글톤 패턴으로
@Configuration
public class HolubSqlConfig {

	private final String dbPath;

	public HolubSqlConfig(@Value("${db.path}") String dbPath)
		throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		String driverName = "com.holub.database.jdbc.JDBCDriver";
		Class.forName(driverName).newInstance();
		this.dbPath = dbPath;
	}

	public JDBCConnection getConnection() throws SQLException {
		String path;
		String os = System.getProperty("os.name").toLowerCase();

		if(os.contains("win")) {
			path = "file:/" + new FileSystemResource("").getFile().
					getAbsolutePath().replace("\\", "/") + "/" + dbPath;
		} else {
			path = "file://" + new FileSystemResource("").getFile().getAbsolutePath() + "/" + dbPath;
		}

		return (JDBCConnection)DriverManager.getConnection(path);
	}
}
