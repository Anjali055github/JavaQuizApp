package com.quizapp.interfaces;

import com.quizapp.databaseClasses.Student;

public interface StudentInterface {
	
	public void createStudent(Student student);
	public int getIdByUserName(String userName);
	
	//option:5 of userModule
	public void getScoreByUserNameAndPassword(String username,String password);

}
