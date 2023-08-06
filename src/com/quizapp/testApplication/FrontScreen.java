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
import com.quizapp.interfaces.LoginInterface;
import com.quizapp.interfaces.QuestionInterface;
import com.quizapp.interfaces.ResultInterface;
import com.quizapp.interfaces.StudentInterface;

public class FrontScreen {
	boolean exitStatusMain = false;
	public static String LoggedInUserName = null;
	public static boolean checkStudentGivenTest = false;
	ResultInterface resultimp = null;
	StudentInterface studentService = null;
	QuestionInterface questionObj = null;

	public void loginScreen() {
		Scanner scan = new Scanner(System.in);
		do {
			try {

				System.out.println("1. Admin / Existing Student Login ");
				System.out.println("2. New Student Registration");
				System.out.println("3. Exit");
				System.out.println("----------------------------------------------");
				System.out.println("Enter your choice from the option given above:");
				System.out.println("------------------------------------------------");
				int choice = scan.nextInt();

				if (choice == 1) {
					System.out.println("Enter your Username:");
					String userName = scan.next();
					System.out.println("Enter your Password:");
					String password = scan.next();

					System.out.println("Please wait while we verify your credentials....\n");

					// to check whether entered user is admin or student from the database
					LoginInterface login = new LoginImpl();
					String role = login.getRoleByUserIdAndPassword(userName, password);
					if (role != null) {
						if (role.equals("admin")) {
							LoggedInUserName = userName;
							this.adminPanel();

						} else {
							LoggedInUserName = userName;
							this.studentPanel();
						}
					} else {
						System.out.println("!**** Invalid Credentials!Enter correct Username and Password ****!\n");
					}
				} else if (choice == 2) {
					System.out.println("=========You have selected :New Student Registration =========\n");
					Student student = StudentImpl.getStudentInput();
					if (student != null) {
						StudentInterface studentService = new StudentImpl();
						// insert student data in database
						studentService.createStudent(student);
					}

				} else if (choice == 3) {
					System.out.println("Do you really want to exit?(y/n)");
					String status = scan.next();
					if (status.equals("yes") || status.equals("Y") || status.equals("y")) {

						exitStatusMain = true;
						System.out.println("!!----Thank you for using  Quiz  Application ----!!");

					}
				} else {
					System.out.println("!***Invalid choice! Please try again.***!");
				}
			} catch (InputMismatchException e) {
				System.out.println("!***** Input mismatch!!Enter valid number format *****!\n");
				scan.next();

			}
		} while (!exitStatusMain);
		scan.close();

	}

	// Student Control Panel//
	public void studentPanel() {
		Scanner scan = new Scanner(System.in);
		int userChoice = 0;
		boolean exitFlagStudent = false;
		String uName, password;

		do {

			System.out.println("\t\t\t!!User Operation!!\r\n" + "\t1. Start your quiz\r\n"
					+ "\t2. Store Quiz result into database\r\n" + "\t3. Display Quiz result\r\n" + "\t4. Exit\r\n");
			System.out.println("------------------------------------------------");
			System.out.println("Enter your choice from the option given above!!");
			System.out.println("------------------------------------------------");
			userChoice = scan.nextInt();

			switch (userChoice) {
			
			case 1:
				System.out.println("=====You have selected : Start your quiz=====\n");

				questionObj = new QuestionImp();
				questionObj.showAllQuestionToStudent();
				break;
			case 2:
				System.out.println("=====You have selected :Store Quiz result into database =====\n");
				if (LoggedInUserName != null) {
					if (checkStudentGivenTest) {
						System.out.println("Your result has been recorded Successfully");
					} else {
						System.out.println("!***** You havent given the test!!Please give your Test *****!\n");

					}

				}

				break;
			case 3:
				System.out.println("=========You have selected :Display Quiz result =========\n");
				System.out.println("Enter the UserName");
				uName = scan.next();
				System.out.println("Enter the Password");
				password = scan.next();
				studentService = new StudentImpl();
				studentService.getScoreByUserNameAndPassword(uName, password);
				break;

			case 4:
				exitFlagStudent = true;
				exitStatusMain = true;
				break;
			}
		} while (!exitFlagStudent);

		// scan.close();
		System.out.println("!!Thank you for using JAVA Quiz application!!");
	}

	// Admin Control Panel//
	public void adminPanel() {
		Scanner scan = new Scanner(System.in);
		int adminChoice = 0;
		boolean exitFlagAdmin = false;

		do {
			try {
				System.out.println(
						"\t\t!!Admin Operation!!\r\n\n" + "1. Display  students score as per ascending order\r\n"
								+ "2. Fetch student score by using id\r\n"
								+ "3. Add question with 4 options into database\r\n" + "4. Exit");
				System.out.println("------------------------------------------------");
				System.out.println("Enter your choice from the option given above!!");
				System.out.println("------------------------------------------------");
				adminChoice = scan.nextInt();

				switch (adminChoice) {
				case 1:
					System.out.println("--------------------------------------------------");
					System.out.println("1.Display  students score as per ascending order!!");
					System.out.println("--------------------------------------------------");
					ResultInterface resultimpl = new ResultImpl();
					resultimpl.displayFnameLnameScore();
					break;
				case 2:
					System.out.println("------------------------------------------");
					System.out.println("2.Fetch student score by using UserId");
					System.out.println("------------------------------------------");
					System.out.println("Enter Student id>>");
					int id = scan.nextInt();
					resultimp = new ResultImpl();
					int score = resultimp.getScoreOnStudentId(id);

					if (score >= 0) {
						System.out.println("Score is>>" + score);
					} else {
						System.out.println("No data on this id");
					}
					break;

				case 3:
					System.out.println("----------------------------------------------");
					System.out.println("3. Add question with 4 options into database");
					System.out.println("----------------------------------------------");
					QuestionImp questionimpl = new QuestionImp();
					questionimpl.addQuestion();
					break;

				case 4:
					exitFlagAdmin = true;
					exitStatusMain = true;
					System.out.println("!!Thank you for using JAVA Quiz application!!");
					break;

				default:
					System.out.println("!****We dont have any operation on this choice. Enter(1-4)****!\n");
					continue;
				}
				if (exitFlagAdmin == false && exitStatusMain == false) {
					System.out.println("Do you want to exit?(y/n)");
					String status = scan.next();
					if (status.equals("yes") || status.equals("Y") || status.equals("y")) {
						exitStatusMain = true;
						System.out.println("--------------!!Thanks you for using this application!!--------------");
						break;
					}
				}

			} catch (InputMismatchException e) {
				System.out.println("!!***** Something went wrong with your input:Enter valid number format *****!");

				scan.next();
			}
		} while (exitFlagAdmin == false);

		// scan.close();
	}

}
