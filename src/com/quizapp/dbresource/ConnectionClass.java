package com.quizapp.dbresource;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {

	static Connection con = null;

	public static Connection getConnectionDetails() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp", "root", "root");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
