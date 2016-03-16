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
	
	public static boolean followFriend(String email, String friendEmail) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "INSERT INTO user_has_friend (`user_email`, `friend_user_email`) VALUES (?, ?)";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, friendEmail);
			stmt.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean unfollowFriend(String email, String friendEmail) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "DELETE FROM user_has_friend WHERE `user_email` = ? AND `friend_user_email` = ?" ;
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, friendEmail);
			stmt.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public static PositionModel getLastPosition(String email) {
		try{
			PositionModel pos = new PositionModel();
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT `lat`, `long` FROM user WHERE `user_email` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				pos.setLat(rs.getDouble("lat"));
				pos.setLon(rs.getDouble("long"));
				return pos;
			}
			return null;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
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
