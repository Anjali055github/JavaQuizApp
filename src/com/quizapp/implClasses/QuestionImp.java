package com.quizapp.implClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;
import com.quizapp.databaseClasses.Question;
import com.quizapp.interfaces.QuestionInterface;
import com.quizapp.interfaces.ResultInterface;
import com.quizapp.interfaces.StudentInterface;
import com.quizapp.testApplication.TestApp;

public class QuestionImp implements QuestionInterface{
	
	Connection connection=null;
	PreparedStatement psStatement=null;
	String rightAnswer;
	Question question=null;
	
	//to know who is logged in
	String LoggedUserName=TestApp.LoggedInUserName;

	//ask admin to add the question
	public void addQuestion() 
	{
			Scanner scan=new Scanner(System.in);
			int qid;
			String questionName;
			String opt1;
			String opt2;
			String opt3;
			String opt4;
			int  CorrectAnswer;
			
			System.out.println("Please Enter Question : -");
			questionName = scan.nextLine();
			
			System.out.println("Please Enter 1st Option : -");
			opt1 = scan.nextLine();
	
			System.out.println("Please Enter 2nd Option : -");
			opt2 = scan.nextLine();
	
			System.out.println("Please Enter 3rd Option : -");
			opt3 = scan.nextLine();
	
			System.out.println("Please Enter 4th Option : -");
			opt4 = scan.nextLine();
	//		This will ask to add Right Answer
			System.out.println("Please Enter the right option(from the above options) :");
			CorrectAnswer = scan.nextInt();
			
			//call to createquestion()to add record in db
			//createQuestions(questionName,opt1,opt2,opt3,opt4,opt5,CorrectAnswer);
			
			//call to question class constrctor
			question=new Question(questionName, opt1, opt2, opt3, opt4, CorrectAnswer);
			
			this.createQuestions(question);
			
	}
	
	
	public void showAllQuestionToStudent() 
	{
		int questionCount=0;
		int correctAnswerCount=0;
		int skippedQuestionCount=0;
		int wrongAnswerCount=0;
		int score=0;
		String grade=null;
		
		Scanner scan=new Scanner(System.in);
		ArrayList<Question> questionList = this.listAllQuestion();
		Collections.shuffle( questionList ) ;
		
		for(Question q:questionList) {
			questionCount++;
			System.out.println("Q."+questionCount+": "+q.getQuestion());
			System.out.println("1: "+q.getOption1());
			System.out.println("2: "+q.getOption2());
			System.out.println("3: "+q.getOption3());
			System.out.println("4: "+q.getOption4());
			System.out.println("5: "+q.getOption5());
			
			System.out.println("Enter your answer: ");
			int answercode=scan.nextInt();
			if(answercode>=1 &&answercode<=5) {
				if(answercode==q.getRightAnswer()) {
					correctAnswerCount++;
					score++;
				}
				else if(answercode!=q.getRightAnswer() && answercode!=5) {
					wrongAnswerCount++;
				}
				else if(answercode==5) {
					skippedQuestionCount++;
				}	
			}
			else {
				System.out.println("Entered invalid option - considered as skipped question.");
				skippedQuestionCount++;
			}
		}
		
		// 4/10*100 = 40% // 
		
		if(correctAnswerCount/questionCount*100 >= 70) 		//Distinction//
			grade = "A+";
		else if(correctAnswerCount/questionCount*100 >= 60) //First Class//
			grade = "A";
		else if(correctAnswerCount/questionCount*100 >= 50) //Second Class//
			grade = "B";
		else if(correctAnswerCount/questionCount*100 >= 35) //Third Class//
			grade = "C";
		else //Fail//
			grade = "D";
		
		TestApp.checkStudentGivenTest=true;
		
		//1.call to getIdByUserName() to get the logged in userId
		StudentInterface studentimp=new StudentImpl();
		int userId=studentimp.getIdByUserName(LoggedUserName);
		System.out.println("-----------------"+userId);
		
		
		//insert the complete resultData of the student in the database
		//call to resultInterface
		ResultInterface resultImpl=new ResultImpl();
		resultImpl.insertStudentResult(questionCount,correctAnswerCount,skippedQuestionCount,wrongAnswerCount,score,grade, userId);
		
	/*	
		System.out.println("CorrectAnswerCount"+correctAnswerCount);
		System.out.println("WrongAnswerCount="+wrongAnswerCount);
		System.out.println("SkippedQuestionCount="+skippedQuestionCount);
		System.out.println("Your score is:" + correctAnswerCount + "/" + questionCount);
		System.out.println("Your Grade Is="+grade);
	*/
	}
	
	
	
	public void createQuestions(Question question) {
		System.out.println(question);
		try {		
			String query="insert into quest (Question, Option1, Option2, Option3, Option4, Option5, RightAnswer) values(?,?,?,?,?,?,?)";
			
			//connect to database
				connection=ConnectionClass.getConnectionDetails();
				psStatement = connection.prepareStatement(query);
				psStatement.setString(1,question.getQuestion() );
				psStatement.setString(2,question.getOption1() );
				psStatement.setString(3,question.getOption2() );
				psStatement.setString(4,question.getOption3() );
				psStatement.setString(5,question.getOption4());
				psStatement.setString(6,question.getOption5() );
				psStatement.setInt(7, question.getRightAnswer());
				
				//send to the database
				int count= psStatement.executeUpdate();
				
				
				
				if(count==1) {
					System.out.println("Question Added Successfully...");
				}else {
					System.out.println("Something went wrong while adding Question...");
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}		
		}
		
			
		
	@Override
	public ArrayList<Question> listAllQuestion() {
		ArrayList<Question>questionList=new ArrayList<>();
	
		try {
			//create the connection object
			connection=ConnectionClass.getConnectionDetails();
			//create the statement object
			String query="select * from  quest";
			psStatement = connection.prepareStatement(query);
			ResultSet resultset  =psStatement.executeQuery();
		
			while(resultset.next()) {
				Question question=new Question();
				question.setQuestion(resultset.getString(2));
				question.setOption1(resultset.getString(3));
				question.setOption2(resultset.getString(4));
				question.setOption3(resultset.getString(5));
				question.setOption4(resultset.getString(6));
				question.setOption5(resultset.getString(7));
				question.setRightAnswer(resultset.getInt(8));
				
				//add the complete object to database
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
