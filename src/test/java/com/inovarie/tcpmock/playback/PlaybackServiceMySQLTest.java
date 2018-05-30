package com.inovarie.tcpmock.playback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

public class PlaybackServiceMySQLTest {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:5555/myappdb?verifyServerCertificate=false&useSSL=false&requireSSL=false";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "1234";
	
	PlaybackService playbackService;
	
	@Before
	public void setUp() throws Exception {
		playbackService = new PlaybackService();
		playbackService.startPlaybackDetached(5555, "MySQLTest");
	}

	@Test
	public void testMySQL() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM tasks";
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			System.out.println("querying SELECT * FROM tasks");
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}

}

