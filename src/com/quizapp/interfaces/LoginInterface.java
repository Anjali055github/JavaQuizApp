package com.quizapp.interfaces;

import com.quizapp.databaseClasses.Login;

public interface LoginInterface {
	boolean validateLoginCredential(String username, String password);

	void createLoginCredential(Login login);

	String getRoleByUserIdAndPassword(String userId, String password);

}
