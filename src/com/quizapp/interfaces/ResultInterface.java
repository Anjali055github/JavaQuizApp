package com.quizapp.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public interface ResultInterface {

	
	
	
	public void insertStudentResult(int questionCount, int correctAnswerCount, int skippedQuestionCount,int wrongAnswerCount, int score, String grade,int studentId);
	
	//public ArrayList getStudentListOnScore(int score);
	public void  displayFnameLnameScore();
	public int  getScoreOnStudentId(int StudentId);
}
