package com.quizapp.interfaces;

import java.util.ArrayList;

import com.quizapp.databaseClasses.Question;

public interface QuestionInterface {
	public void addQuestion();

	void updateQuestion();

	void deleteQuestion();

	void showAllQuestionToStudent();

	ArrayList<Question> listAllQuestion();
}
