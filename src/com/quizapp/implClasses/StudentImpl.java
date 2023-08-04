package com.quizapp.implClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.quizapp.databaseClasses.Login;
import com.quizapp.databaseClasses.Student;
import com.quizapp.interfaces.LoginService;
import com.quizapp.interfaces.ResultInterface;
import com.quizapp.interfaces.StudentInterface;

public class StudentImpl implements StudentInterface {
	
	
	Connection connection=null;
	PreparedStatement psStatement=null;

	public static  Student getStudentInput() {
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the first name");
		String fisrtName=scan.nextLine();
		
		System.out.println("Enter the last name");
		String  lastName=scan.nextLine();
		
		System.out.println("Enter the username ");
		String userName=scan.nextLine();
		
		System.out.println("Enter the password ");
		String password=scan.nextLine();
		
		
		System.out.println("Enter the city");
		String city=scan.nextLine();
		

		System.out.println("Enter the mailId");
		String mailId=scan.nextLine();
		
		System.out.println("Enter the mobile number");
		long mobileNo=scan.nextLong();
		
		//student class constructor
		Student studentObj=new Student(fisrtName, lastName, city, mobileNo, mailId, password, userName);
		System.out.println(studentObj);
		
		return studentObj;
		
	}
	

	@Override
	public void createStudent(Student student) {
		String query="insert into student(FirstName,LastName,City,MobileNo,MailId,SetPassword,UserName)values(?,?,?,?,?,?,?)";
		try {
			//get the connection object
			connection = ConnectionClass.getConnectionDetails();
			
			//create the Statement object
			psStatement= connection.prepareStatement(query);
			psStatement.setString(1, student.getFirstName());
			psStatement.setString(2, student.getLastName());
			psStatement.setString(3, student.getCity());
			psStatement.setLong(4,student.getMobileNo());
			psStatement.setString(5, student.getMailId());
			psStatement.setString(6, student.getSetPassword());
			psStatement.setString(7, student.getUserName());
			
			//send to the database
			int insertCount = psStatement.executeUpdate();
			System.out.println(insertCount+" record inserted successfully");
			
			//to enter the logincredetials in the logindetails table
			Login login=new Login(student.getUserName(), student.getSetPassword());
			
			//call to the loginServiceImpl Class
			LoginService loginservice=new LoginImpl();
			loginservice.createLoginCredential(login);
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				
				psStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	
	

public int getIdByUserName(String userName) {
	int uId=0;
	String query="select StudentId from  student where Username=?";
	try {
		connection=ConnectionClass.getConnectionDetails();
		psStatement= connection.prepareStatement(query);
		psStatement.setString(1, userName);
		
		
		ResultSet resultSet = psStatement.executeQuery();
		
		if(resultSet.next()) {
			uId=resultSet.getInt(1);
			
		}
			
	}
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
	finally {
		try {
			connection.close();
			psStatement.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		
		}	
	return uId;
}

//option 5
@Override
public void getScoreByUserNameAndPassword(String username, String password) {
	int studentscore=-1;
	
	//1.fetch the id based on username
	int userId = getIdByUserName(username);
	
	//2.get the score based on userid from resulttable;
		ResultInterface result=new ResultImpl();
		studentscore=result.getScoreOnStudentId(userId);
		if(studentscore>=0) {
			System.out.println("Your score is:"+studentscore);
		}
		else {
			System.out.println("No test given ");
		}
	
	
	
}

	
	
	
	
	

}
