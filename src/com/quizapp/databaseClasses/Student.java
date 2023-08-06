package com.quizapp.databaseClasses;

public class Student {

	private int studentId;
	private String firstName;
	private String lastName;
	private String city;
	private long mobileNo;
	private String mailId;
	private String setPassword;
	private String userName;

	public Student(String firstName, String lastName, String city, long mobileNo, String mailId, String setPassword,
			String userName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.mobileNo = mobileNo;
		this.mailId = mailId;
		this.setPassword = setPassword;
		this.userName = userName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getSetPassword() {
		return setPassword;
	}

	public void setSetPassword(String setPassword) {
		this.setPassword = setPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName + ", city="
				+ city + ", mobileNo=" + mobileNo + ", mailId=" + mailId + ", setPassword=" + setPassword
				+ ", userName=" + userName + "]";
	}

}
