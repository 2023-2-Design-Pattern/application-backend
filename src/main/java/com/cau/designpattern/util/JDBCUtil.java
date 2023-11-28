package com.cau.designpattern.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.holub.database.jdbc.JDBCStatement;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCUtil {

	/**
	 * Table에서 가장 큰 id 값을 찾고, 1 증가시킨 값을 반환합니다.
	 * @param stmt
	 * @param tableName Table명
	 * @return 새로 생성된 id
	 */
	public static long generateId(JDBCStatement stmt, String tableName) {

		try {
			ResultSet rs = stmt.executeQuery(
				String.format("SELECT %sId FROM %s", tableName, tableName));
			long id = 0;
			while (rs.next()) // order by 오류로 임시방편
				id = rs.getLong(String.format("%sId", tableName)) + 1;

			stmt.close();
			return id;
		} catch (NullPointerException | SQLException e) { // Table이 비어있으면 id는 0부터 시작
			return 0;
		}
	}
}
