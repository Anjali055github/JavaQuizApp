package com.quizapp.implClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.quizapp.databaseClasses.Login;
import com.quizapp.dbresource.ConnectionClass;
import com.quizapp.interfaces.LoginInterface;

public class LoginImpl implements LoginInterface {

	Connection connection = null;
	PreparedStatement psStatement = null;

	// allow user to enter the username and password
	public void getUserInputCredential() {

		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the username");
		String uName = scan.nextLine();

		System.out.println("Enter the password");
		String uPassword = scan.nextLine();

		// to check whether the given userName and password match in the database
		LoginInterface login = new LoginImpl();
		boolean validUser = login.validateLoginCredential(uName, uPassword);

		System.out.println(validUser);
		if (validUser) {
			System.out.println("Succesfully Logged In");
		} else {
			System.out.println("Enter  correct Username and Password");
		}
	}

	@Override
	public void createLoginCredential(Login login) {
		String query = "insert into logindetails(Username,Password,UserRole )values(?,?,?)";
		try {
			connection = ConnectionClass.getConnectionDetails();
			psStatement = connection.prepareStatement(query);
			psStatement.setString(1, login.getUsernames());
			psStatement.setString(2, login.getPassword());
			psStatement.setString(3, login.getUserRole());
			int insertcount = psStatement.executeUpdate();
			System.out.println(insertcount + "inserted");

			connection.close();
			psStatement.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	@Override
	public boolean validateLoginCredential(String username, String password) {

		boolean checkValidUser = false;
		String query = "select * from  logindetails where Username=? and Password=?";
		try {
			connection = ConnectionClass.getConnectionDetails();
			psStatement = connection.prepareStatement(query);
			psStatement.setString(1, username);
			psStatement.setString(2, password);

			ResultSet resultSet = psStatement.executeQuery();
			if (resultSet.next()) {
				checkValidUser = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				psStatement.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return checkValidUser;
	}

	@Override
	public String getRoleByUserIdAndPassword(String userId, String password) {
		String userRole = null;
		String query = "select UserRole from  logindetails where Username=? and Password=?";
		try {
			connection = ConnectionClass.getConnectionDetails();
			psStatement = connection.prepareStatement(query);
			psStatement.setString(1, userId);
			psStatement.setString(2, password);

			ResultSet resultSet = psStatement.executeQuery();

			if (resultSet.next()) {
				userRole = resultSet.getString("UserRole");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				psStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return userRole;
	}

}
