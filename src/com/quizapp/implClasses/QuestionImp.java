package com.quizapp.implClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;
import com.quizapp.databaseClasses.Question;
import com.quizapp.dbresource.ConnectionClass;
import com.quizapp.interfaces.QuestionInterface;
import com.quizapp.interfaces.ResultInterface;
import com.quizapp.interfaces.StudentInterface;
import com.quizapp.testApplication.FrontScreen;
import com.quizapp.testApplication.TestApp;

public class QuestionImp implements QuestionInterface {

	Connection connection = null;
	PreparedStatement psStatement = null;
	String rightAnswer;
	Question question = null;

	// to know who is logged in
	String LoggedUserName = FrontScreen.LoggedInUserName;

	// ask admin to add the question
	public void addQuestion() {
		Scanner scan = new Scanner(System.in);
		int qid;
		String questionName = "";
		String opt1 = "";
		String opt2 = "";
		String opt3 = "";
		String opt4 = "";
		int CorrectAnswer = 0;

		try {
			System.out.println("Enter Question:");
			questionName = scan.nextLine();

			System.out.println("Enter 1st Option:");
			opt1 = scan.nextLine();

			System.out.println("Enter 2nd Option:");
			opt2 = scan.nextLine();

			System.out.println("Enter 3rd Option:");
			opt3 = scan.nextLine();

			System.out.println("Enter 4th Option:");
			opt4 = scan.nextLine();

			// This will ask to add Right Answer
			System.out.println("Please enter the correct answer code(1 to 4):");
			CorrectAnswer = scan.nextInt();

			if (questionName.length() != 0 && opt1.length() != 0 && opt2.length() != 0 && opt3.length() != 0
					&& opt4.length() != 0 && CorrectAnswer >= 1 && CorrectAnswer <= 4) {
				// Call to question class constructor
				question = new Question(questionName, opt1, opt2, opt3, opt4, CorrectAnswer);
				this.createQuestions(question);
			} else {
				System.out.println("Input can't be null or answer code can't be other than 1 to 4! Please retry.\n");
			}

		} catch (InputMismatchException e) {

			System.out.println("!!***** Something went wrong with your input:Enter valid number format *****!\n");
			scan.next();
		}

	}

	public void showAllQuestionToStudent() {
		int questionCount = 0;
		int correctAnswerCount = 0;
		int skippedQuestionCount = 0;
		int wrongAnswerCount = 0;
		int score = 0;
		String grade = null;

		Scanner scan = new Scanner(System.in);
		//call to listAllQuestion TO Get all the question from db
		ArrayList<Question> questionList = this.listAllQuestion();
		Collections.shuffle(questionList);

		for (Question q : questionList) {
			try {
				questionCount++;
				System.out.println("Q." + questionCount + ": " + q.getQuestion());
				System.out.println("\n1: " + q.getOption1());
				System.out.println("2: " + q.getOption2());
				System.out.println("3: " + q.getOption3());
				System.out.println("4: " + q.getOption4());
				System.out.println("5: " + q.getOption5());

				System.out.println("\nEnter your answer: ");
				int answercode = scan.nextInt();
				if (answercode >= 1 && answercode <= 5) {
					if (answercode == q.getRightAnswer()) {
						correctAnswerCount++;
						score++;
					} else if (answercode != q.getRightAnswer()) {
						wrongAnswerCount++;
					} else if (answercode == 5) {
						skippedQuestionCount++;
					}
				} else {
					System.out.println("Entered invalid option - considered as skipped question.");
					skippedQuestionCount++;
				}
			} catch (InputMismatchException e) {
				System.out.println("--------------------------------------------------------------------------");
				System.out.println("Input mismatch! Entered invalid number format; considered as skipped question.");
				System.out.println("--------------------------------------------------------------------------");
				skippedQuestionCount++;
				scan.next();

			}
		}
		System.out.println("Thank you. Your test has been completed succesfully.");

		// 4/No of question*100 = 40% //
		if (correctAnswerCount / questionCount * 100 >= 70) // Distinction//
			grade = "A+";
		else if (correctAnswerCount / questionCount * 100 >= 60) // First Class//
			grade = "A";
		else if (correctAnswerCount / questionCount * 100 >= 50) // Second Class//
			grade = "B";
		else if (correctAnswerCount / questionCount * 100 >= 35) // Third Class//
			grade = "C";
		else // Fail//
			grade = "D";

		FrontScreen.checkStudentGivenTest = true;

		// 1.call to getIdByUserName() to get the logged in userId
		StudentInterface studentimp = new StudentImpl();
		int userId = studentimp.getIdByUserName(LoggedUserName);

		// insert the complete resultData of the student in the database
		// call to resultInterface
		ResultInterface resultImpl = new ResultImpl();
		resultImpl.insertStudentResult(questionCount, correctAnswerCount, skippedQuestionCount, wrongAnswerCount, score,
				grade, userId);

	}

	public void createQuestions(Question question) {
		try {
			String query = "insert into quest (Question, Option1, Option2, Option3, Option4, Option5, RightAnswer) values(?,?,?,?,?,?,?)";

			// connect to database
			connection = ConnectionClass.getConnectionDetails();
			psStatement = connection.prepareStatement(query);
			psStatement.setString(1, question.getQuestion());
			psStatement.setString(2, question.getOption1());
			psStatement.setString(3, question.getOption2());
			psStatement.setString(4, question.getOption3());
			psStatement.setString(5, question.getOption4());
			psStatement.setString(6, question.getOption5());
			psStatement.setInt(7, question.getRightAnswer());

			// send to the database
			int count = psStatement.executeUpdate();

			if (count == 1) {
				System.out.println("Question Added Successfully...");
			} else {
				System.out.println("Something went wrong while adding Question...");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Question> listAllQuestion() {
		ArrayList<Question> questionList = new ArrayList<>();

		try {
			// create the connection object
			connection = ConnectionClass.getConnectionDetails();
			// create the statement object
			String query = "select * from  quest";
			psStatement = connection.prepareStatement(query);
			ResultSet resultset = psStatement.executeQuery();

			while (resultset.next()) {
				Question question = new Question();
				question.setQuestion(resultset.getString(2));
				question.setOption1(resultset.getString(3));
				question.setOption2(resultset.getString(4));
				question.setOption3(resultset.getString(5));
				question.setOption4(resultset.getString(6));
				question.setOption5(resultset.getString(7));
				question.setRightAnswer(resultset.getInt(8));

				// add the complete object to database
				questionList.add(question);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return questionList;
	}

	@Override
	public void updateQuestion() {

	}

	@Override
	public void deleteQuestion() {

	}

}
