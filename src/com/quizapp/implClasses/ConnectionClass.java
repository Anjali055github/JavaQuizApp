package com.quizapp.implClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
	


	static Connection con = null;
	public static Connection getConnectionDetails() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp", "root","root");

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//QuestionImp ques = new QuestionImp(con);
		//ques.addQuestion();
		
		
		return con;
	}
	
	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		ConnectionClass obj = new ConnectionClass();
		Connection con = obj.getConnectionDetails();
	}
	
}
