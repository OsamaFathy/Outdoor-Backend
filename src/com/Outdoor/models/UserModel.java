package com.Outdoor.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Outdoor.models.DBConnection;
import com.Outdoor.models.UserModel;
import com.mysql.jdbc.Statement;

public class UserModel {

	
	private String name;
	private String email;
	private String pass;

	private String question;
	private String answer;
	private String alternative;
	
	private Double lat;
	private Double lon;
	
	public String getPass(){
		return pass;
	}
	
	public void setPass(String pass){
		this.pass = pass;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public static UserModel addNewUser(String name, String email, String pass, String question, String ans,
			String alternative) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "INSERT INTO user(`user_email`, `username`, `password`, `security_question`"
					+ ", `security_answer`, `alternative_email`) VALUES(?,?,?,?,?,?)";
			// System.out.println(sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println(stmt.toString());
			stmt.setString(1, email);
			stmt.setString(2, name);
			stmt.setString(3, pass);
			stmt.setString(4, question);
			stmt.setString(5, ans);
			stmt.setString(6, alternative);
			stmt.executeUpdate();

			int cnt = stmt.getUpdateCount();
			
			if(cnt == 1){
				UserModel user = new UserModel();
				user.email = email;
				user.pass = pass;
				user.name = name;
				user.question = question;
				user.answer = ans;
				user.alternative = alternative;
				user.lat = 0.0;
				user.lon = 0.0;
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOO");
		return null;
	}

	
	
	public static UserModel login(String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			//String sql = "SELECT * FROM user WHERE `email` = ? and `password` = ?";
			String sql = "SELECT * FROM user WHERE `user_email` = ? and `password` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, pass);
			
			System.out.println(stmt.toString());
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				UserModel user = new UserModel();
				user.email = rs.getString("user_email");
				user.pass = rs.getString("password");
				user.name = rs.getString("username");
				user.question = rs.getString("security_question");
				user.answer = rs.getString("security_answer");
				user.alternative = rs.getString("alternative_email");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean updateUserPosition(String email, Double lat, Double lon) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "UPDATE user SET `lat` = ? , `long` = ? where `user_email` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			stmt.setString(3, email);
			stmt.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAlternative() {
		return alternative;
	}

	public void setAlternative(String alternative) {
		this.alternative = alternative;
	}

}