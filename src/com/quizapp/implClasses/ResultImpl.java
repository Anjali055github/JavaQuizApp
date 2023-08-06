package com.quizapp.implClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.quizapp.dbresource.ConnectionClass;
import com.quizapp.interfaces.ResultInterface;

public class ResultImpl implements ResultInterface {

	Connection connection = null;
	PreparedStatement psStatement = null;

	@Override
	public void insertStudentResult(int totalquestionCount, int correctAnswerCount, int skippedQuestionCount,
			int wrongAnswerCount, int score, String grade, int studentId) {
		String query = null;
		boolean checkStudentResultExists = checkStudentResultExists(studentId);
		if (checkStudentResultExists) {
			query = "update result set RightAnswerCount=?,WrongAnswerCount=?,NoAttempCount=?,Grade=?,Score=?,TotalQuestionCount=? where student_id=?";
		} else {
			query = "insert into result(RightAnswerCount,WrongAnswerCount,NoAttempCount,Grade,Score,TotalQuestionCount,student_id)values(?,?,?,?,?,?,?)";

		}

		try {
			connection = ConnectionClass.getConnectionDetails();
			psStatement = connection.prepareStatement(query);
			psStatement.setInt(1, correctAnswerCount);
			psStatement.setInt(2, wrongAnswerCount);
			psStatement.setInt(3, skippedQuestionCount);
			psStatement.setString(4, grade);
			psStatement.setInt(5, score);
			psStatement.setInt(6, totalquestionCount);
			psStatement.setInt(7, studentId);

			int insertcount = psStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				psStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public boolean checkStudentResultExists(int studentId) {
		boolean flagRecordExists = false;
		String query = "select * from result where student_id=? ";
		try {
			connection = ConnectionClass.getConnectionDetails();
			psStatement = connection.prepareStatement(query);
			psStatement.setInt(1, studentId);
			ResultSet resultSet = psStatement.executeQuery();
			while (resultSet.next()) {
				flagRecordExists = true;
				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				psStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return flagRecordExists;

	}

	@Override
	public void displayFnameLnameScore() {

		String query = "select s.FirstName ,s.LastName,r.score  from result r left outer  join  student s on r.student_id =s.StudentId order by Score";
		try {
			connection = ConnectionClass.getConnectionDetails();
			psStatement = connection.prepareStatement(query);
			ResultSet resultSet = psStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("FirstName: " + resultSet.getString(1));
				System.out.println("LastName: " + resultSet.getString(2));
				System.out.println("score:" + resultSet.getString(3));
				System.out.println();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				psStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public int getScoreOnStudentId(int StudentId) {
		int score = -1;

		String query = "select Score from result where student_id=?";
		try {
			connection = ConnectionClass.getConnectionDetails();
			psStatement = connection.prepareStatement(query);
			psStatement.setInt(1, StudentId);
			ResultSet resultSet = psStatement.executeQuery();
			while (resultSet.next()) {
				score = resultSet.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				psStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return score;
	}

}
