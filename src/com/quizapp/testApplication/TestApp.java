package com.quizapp.testApplication;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.quizapp.databaseClasses.Login;
import com.quizapp.databaseClasses.Question;
import com.quizapp.databaseClasses.Student;
import com.quizapp.implClasses.LoginImpl;
import com.quizapp.implClasses.QuestionImp;
import com.quizapp.implClasses.ResultImpl;
import com.quizapp.implClasses.StudentImpl;
import com.quizapp.interfaces.LoginService;
import com.quizapp.interfaces.QuestionInterface;
import com.quizapp.interfaces.ResultInterface;
import com.quizapp.interfaces.StudentInterface;

public class TestApp {
	boolean exitStatusMain=false;
	public static String LoggedInUserName=null;
	public static boolean checkStudentGivenTest=false;
	ResultInterface resultimp=null;
	StudentInterface studentService=null;
	QuestionInterface  questionObj = null;
	
	public  void loginScreen() {
		Scanner scan =new Scanner(System.in);
		
		System.out.println("\t\t\t!! Welcome to Quiz based application !!\n\n");
		do {
			System.out.println("Enter your choice:");
			System.out.println("1. Admin / Existing Sudent");
			System.out.println("2. New Student Registration");
			System.out.println("3. Exit");
			int choice=scan.nextInt();
			
			if(choice==1) {
					System.out.println("Please Enter your User id");
					String userName = scan.next();
					System.out.println("Please Enter your Password");		
					String password = scan.next();
					
					System.out.println("Please wait while we verify your credentials....");
					//to check whether entered user is admin or student from the database
					
					LoginService login=new LoginImpl();
					String role = login.getRoleByUserIdAndPassword(userName, password);
									
					
					if(role!=null) {
						if(role.equals("admin")) {
							LoggedInUserName=userName;
							this.adminPanel();
							
						}
						else {
							LoggedInUserName=userName;
							this.studentPanel();
						}
					}
					else {
						System.out.println("Enter Correct Username and password");
					}
			}
			else if(choice==2) {
					System.out.println("1. StudentRegistration");
					Student student = StudentImpl.getStudentInput();
					StudentInterface studentService=new StudentImpl();
					studentService.createStudent(student);
			}
			else if(choice==3) {
					System.out.println("do you want to exit?(y/n)");
					String status=scan.next();
					if(status.equals("yes")||status.equals("Y")||status.equals("y")) {
						exitStatusMain=true;
						System.out.println("Thanks you for using this application");
					}
			}
			else {
				System.out.println("Invalid choice! Please try again.");
			}
		}while(!exitStatusMain);
			
	}


	//Student Control Panel//
	public   void studentPanel() {
		Scanner scan =new Scanner(System.in);
		int userChoice=0;
		boolean exitFlagStudent=false;
		String uName,password;
		
		
		do {	
			
			System.out.println("\t\t\t!!User Operation!!\r\n" + 
					"\t1. Student Registration\r\n" + 
					"\t2. Student Login\r\n" + 
					"\t3. Start your quiz\r\n" + 
					"\t4. Store Quiz result into database\r\n" + 
					"\t5. Display Quiz result\r\n" +
					"\t6. Exit\r\n" 
					);
			
			System.out.println("Enter your  choice from the given  list(1-5)");
			userChoice=scan.nextInt();
			
			switch(userChoice) {
				case 1:
					System.out.println("\t\t1. StudentRegistration");
					Student student = StudentImpl.getStudentInput();
					 studentService=new StudentImpl();
					studentService.createStudent(student);			
					break;						
				case 2:
					System.out.println("\t\t!!2.Student Login!!\n");
					LoginImpl login=new LoginImpl();
					login.getUserInputCredential();
					break;
				case 3:
					System.out.println("Display the list of questions");
					
					questionObj=new QuestionImp();
					questionObj.showAllQuestionToStudent();
					break;
				case 4:
					System.out.println("\t\tStore Quiz result into database\n");
					if(LoggedInUserName!=null) {
						if(checkStudentGivenTest)	{
							System.out.println("Your result has been recorded Successfully");
						}
						else {
							System.out.println("You havent given the test!!Please give your Test");
							System.out.println();
						}
						
					}
					
					break;
				case 5:
					System.out.println("Enter the username");
					uName=scan.next();
					System.out.println("Enter the password");
					password=scan.next();
					 studentService=new StudentImpl();
					 studentService.getScoreByUserNameAndPassword(uName,password);
					 break;
					
					
				case 6:
					exitFlagStudent=true;
					exitStatusMain=true;
					break;	
			}
		}while(!exitFlagStudent);
	
		//scan.close();
		System.out.println("!!Thank you for using JAVA Quiz application!!");
	}
	
	
	//Admin Control Panel//
	public void adminPanel() {
		Scanner scan =new Scanner(System.in);
		int adminChoice=0;
		boolean exitFlagAdmin=false;
		try {
			do {
				System.out.println("\t\t!!Admin Operation!!\r\n\n" + 
						"1. Display  students score as per ascending order\r\n" + 
						"2. Fetch student score by using id\r\n" + 
						"3. Add question with 4 options into database\r\n" +
						"4. Exit");
				System.out.println("Enter your choice");
				adminChoice=scan.nextInt();
				
				switch(adminChoice) {
				case 1:
					System.out.println("\t!!Display  students score as per ascending order!!");
					ResultInterface resultimp=new ResultImpl();
					resultimp.displayFnameLnameScore();
					break;
				case 2:
					System.out.println("\t!!Fetch student score by using id!!");
					System.out.println("Enter Student id>>");
					int id=scan.nextInt();
					resultimp=new ResultImpl();
					int score=resultimp.getScoreOnStudentId(id);
					
					 if(score>=0) {
						System.out.println("Score is>>"+score);
					 }
					 else {
						System.out.println("No data on this id");
					 }
					break;
					
				case 3:
					System.out.println("8. Add question with 4 options into database\n");
					QuestionImp questionimpl=new QuestionImp();
					questionimpl.addQuestion();	
					break;
	
				case 4:
					exitFlagAdmin=true;
					exitStatusMain=true;
					System.out.println("!!Thank you for using JAVA Quiz application!!");
					break;
					
				default:System.out.println("Invalid option");
				}
				if(exitFlagAdmin == false && exitStatusMain == false) {
					System.out.println("do you want to exit?(y/n)");
					String status=scan.next();
					if(status.equals("yes")||status.equals("Y")||status.equals("y")) {
						exitStatusMain=true;
						System.out.println("!!Thanks you for using this application!!");
						break;
					}
				}
			}while(exitFlagAdmin == false);
			
			
		}catch(InputMismatchException e) {
			System.out.println("Something went wrong with your input:"+e.getMessage());
			scan.next();
		}
		catch(Exception e) {
			System.out.println("Something went wrong:"+e.getMessage());
			scan.next();
		}
		
		//scan.close();
	}

	//Start Of JAVA Quiz Application//
	public static void main(String[] args) {
		TestApp obj=new TestApp();
		obj.loginScreen();
	}
}
