package com.quizapp.databaseClasses;

public class Login {

	private int loginId;
	private String usernames;
	private String Password;
	private String UserRole = "user";

	public Login(String usernames, String password, String userRole) {
		super();
		this.usernames = usernames;
		Password = password;
		UserRole = userRole;
	}

	public Login(String usernames, String password) {
		super();
		this.usernames = usernames;
		Password = password;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getUsernames() {
		return usernames;
	}

	public void setUsernames(String usernames) {
		this.usernames = usernames;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}

	@Override
	public String toString() {
		return "Login [loginId=" + loginId + ", usernames=" + usernames + ", Password=" + Password + ", UserRole="
				+ UserRole + "]";
	}

}
