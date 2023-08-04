package com.quizapp.interfaces;

import com.quizapp.databaseClasses.Login;

public interface LoginService {

	//void enterLoginDetails(String email, String Password, String Role);

	//void updateLoginDetails(String email, String Password, String Role);
	
	boolean validateLoginCredential(String username,String password);
	void createLoginCredential(Login login);
	
	String getRoleByUserIdAndPassword(String userId,String password);

	
}
