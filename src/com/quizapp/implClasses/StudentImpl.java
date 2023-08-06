package com.quizapp.implClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.quizapp.databaseClasses.Login;
import com.quizapp.databaseClasses.Student;
import com.quizapp.dbresource.ConnectionClass;
import com.quizapp.interfaces.LoginInterface;
import com.quizapp.interfaces.ResultInterface;
import com.quizapp.interfaces.StudentInterface;

public class StudentImpl implements StudentInterface {

	Connection connection = null;
	PreparedStatement psStatement = null;
	static Student studentObj = null;

	public static Student getStudentInput() {
		Scanner scan = new Scanner(System.in);
		try {
			System.out.println("Enter the First Name");
			String fisrtName = scan.nextLine();

			System.out.println("Enter the Last Name");
			String lastName = scan.nextLine();

			System.out.println("Enter the Username ");
			String userName = scan.nextLine();

			System.out.println("Enter the Password ");
			String password = scan.nextLine();

			System.out.println("Enter the City");
			String city = scan.nextLine();

			System.out.println("Enter the MailId");
			String mailId = scan.nextLine();

			System.out.println("Enter the Mobile Number");
			long mobileNo = scan.nextLong();

			// student class constructor
			studentObj = new Student(fisrtName, lastName, city, mobileNo, mailId, password, userName);
			System.out.println("Registration is in progrss. Please wait...");
			// System.out.println(studentObj);

		} catch (InputMismatchException e) {
			System.out.println("!***** Input mismatch!!Enter valid number format *****!\n");
		}

		return studentObj;
	}

	@Override
	public void createStudent(Student student) {
		String query = "insert into student(FirstName,LastName,City,MobileNo,MailId,SetPassword,UserName)values(?,?,?,?,?,?,?)";
		try {
			// Get the connection object
			connection = ConnectionClass.getConnectionDetails();

			// Create the Statement object
			psStatement = connection.prepareStatement(query);
			psStatement.setString(1, student.getFirstName());
			psStatement.setString(2, student.getLastName());
			psStatement.setString(3, student.getCity());
			psStatement.setLong(4, student.getMobileNo());
			psStatement.setString(5, student.getMailId());
			psStatement.setString(6, student.getSetPassword());
			psStatement.setString(7, student.getUserName());

			// Send to the database
			psStatement.executeUpdate();
			System.out.println("Registation successful. Please go ahead with exploring new options.");

			// To enter the logincredetials in the logindetails table
			Login login = new Login(student.getUserName(), student.getSetPassword());

			// Call to the loginServiceImpl Class
			LoginInterface loginservice = new LoginImpl();
			loginservice.createLoginCredential(login);

		} catch (SQLIntegrityConstraintViolationException e2) {
			System.out.println("\n!****Username '" + student.getUserName()
					+ "' has already been registered. Please retry with another.****!\n");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				psStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int getIdByUserName(String userName) {
		int uId = 0;
		String query = "select StudentId from  student where Username=?";
		try {
			connection = ConnectionClass.getConnectionDetails();
			psStatement = connection.prepareStatement(query);
			psStatement.setString(1, userName);

			ResultSet resultSet = psStatement.executeQuery();

			if (resultSet.next()) {
				uId = resultSet.getInt(1);
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
		return uId;
	}

	// Option 5
	@Override
	public void getScoreByUserNameAndPassword(String username, String password) {
		int studentscore = -1;

		// 1.fetch the id based on username
		int userId = getIdByUserName(username);

		// 2.get the score based on userid from resulttable;
		ResultInterface result = new ResultImpl();
		studentscore = result.getScoreOnStudentId(userId);
		if (studentscore >= 0) {
			System.out.println("----------------------------");
			System.out.println("Your score is:" + studentscore);
			System.out.println("----------------------------");
		} else {
			System.out.println("----------------------------");
			System.out.println("You havent Given your test!! ");
			System.out.println("----------------------------");
		}
	}
}
