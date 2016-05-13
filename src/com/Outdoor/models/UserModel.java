package com.Outdoor.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	private Double latitude;
	private Double longitude;
	
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
		return latitude;
	}

	public void setLat(Double lat) {
		this.latitude = lat;
	}

	public Double getLon() {
		return longitude;
	}

	public void setLon(Double lon) {
		this.longitude = lon;
	}
	
	/**
	 * 
	 * @param name username of user
	 * @param email his email
	 * @param pass his password
	 * @param question his security question
	 * @param ans his security answer
	 * @param alternative his alternative email
	 * @return the newly created user or null of case of error
	 */
	public static UserModel addNewUser(String name, String email, String pass, String question, String ans,
			String alternative) {
		try {
			Connection connection = DBConnection.getActiveConnection();
			String sql = "INSERT INTO user(`user_email`, `username`, `password`, `security_question`"
					+ ", `security_answer`, `alternative_email`) VALUES(?,?,?,?,?,?)";

			PreparedStatement statement;
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, email);
			statement.setString(2, name);
			statement.setString(3, pass);
			statement.setString(4, question);
			statement.setString(5, ans);
			statement.setString(6, alternative);
			statement.executeUpdate();

			int count = statement.getUpdateCount();
			
			if(count == 1){
				UserModel user = new UserModel();
				user.email = email;
				user.pass = pass;
				user.name = name;
				user.question = question;
				user.answer = ans;
				user.alternative = alternative;
				user.latitude = 0.0;
				user.longitude = 0.0;
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param email email of the user who wants to log-in
	 * @param pass password of the user who want to log-in
	 * @return an object of type userModel if logged-in successfully of null otherwise
	 */
	public static UserModel login(String email, String pass) {
		try {
			Connection connection = DBConnection.getActiveConnection();
			//String sql = "SELECT * FROM user WHERE `email` = ? and `password` = ?";
			String sql = "SELECT * FROM user WHERE `user_email` = ? and `password` = ?";
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, pass);
			
			ResultSet result = statement.executeQuery();
			
			if (result.next()) {
				UserModel user = new UserModel();
				user.email = result.getString("user_email");
				user.pass = result.getString("password");
				user.name = result.getString("username");
				user.question = result.getString("security_question");
				user.answer = result.getString("security_answer");
				user.alternative = result.getString("alternative_email");
				user.latitude = result.getDouble("lat");
				user.longitude = result.getDouble("long");
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param email email of the user we want to get his user name
	 * @return the user name of the given user of null if something failed
	 */
	public static String getUserName(String email) {
		try {
			Connection connection = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM user WHERE `user_email` = ?";
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);
			statement.setString(1, email);

			ResultSet result = statement.executeQuery();
			
			if (result.next()) {
				return result.getString("username");
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * checks if the first user follows the second user
	 * @param myEmail email of the first user
	 * @param hisEmail email of the second user
	 * @return true if he follows him or false otherwise
	 */
	public static boolean Followed(String myEmail, String hisEmail) {
		try {
			Connection connection = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM `user_has_friend` WHERE `user_email` = ? AND `friend_user_email` = ?";
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);
			statement.setString(1, myEmail);
			statement.setString(2, hisEmail);

			ResultSet result = statement.executeQuery();
			
			return result.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param email email of the user we want to update his position
	 * @param latitude the new latitude 
	 * @param longitude the new longitude
	 * @return true of operation was successful or false otherwise
	 */
	public static boolean updateUserPosition(String email, Double latitude, Double longitude) {
		try{
			Connection connection = DBConnection.getActiveConnection();
			String sql = "UPDATE user SET `lat` = ? , `long` = ? where `user_email` = ?";
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);
			statement.setDouble(1, latitude);
			statement.setDouble(2, longitude);
			statement.setString(3, email);
			statement.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param email email of the user who wants to follow the other user
	 * @param friendEmail email of the user who will be followed by the first user
	 * @return true if operation was successful or false otherwise
	 */
	public static boolean followFriend(String email, String friendEmail) {
		try{
			Connection connection = DBConnection.getActiveConnection();
			String sql = "INSERT INTO user_has_friend (`user_email`, `friend_user_email`) VALUES (?, ?)";
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, friendEmail);
			statement.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param email email of the user who wants to unfollow the other user
	 * @param friendEmail email of the user who will be unfollowed by the first user
	 * @return true if operation was successful or false otherwise
	 */
	public static boolean unfollowFriend(String email, String friendEmail) {
		try{
			Connection connection = DBConnection.getActiveConnection();
			String sql = "DELETE FROM user_has_friend WHERE `user_email` = ? AND `friend_user_email` = ?" ;
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, friendEmail);
			statement.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param email email of user we want to get his last position
	 * @return an object of positionModel with the longitude and latitude of last position of the given user
	 */
	public static PositionModel getLastPosition(String email) {
		try{
			PositionModel position = new PositionModel();
			Connection connection = DBConnection.getActiveConnection();
			String sql = "SELECT `lat`, `long` FROM user WHERE `user_email` = ?";
			PreparedStatement statement;
			System.out.println(sql);
			statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				position.setLat(result.getDouble("lat"));
				position.setLon(result.getDouble("long"));
				return position;
			}
			return null;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param email email of the user want to retrieve his followers
	 * @return an arrayList of users who follow the given user or null something failed
	 */
	public static ArrayList<UserModel> getFollowers(String email){
		try{
			ArrayList<UserModel> followers = new ArrayList<>();
			Connection connection = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM `user` WHERE `user_email` IN "
					+ "(SELECT `user_email` FROM `user_has_friend`"
					+ "WHERE `friend_user_email`=?)";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setString(1, email);
			ResultSet result = statment.executeQuery();
			while(result.next()){
				UserModel user = new UserModel();
				user.email = result.getString("user_email");
				user.pass = result.getString("password");
				user.name = result.getString("username");
				user.question = result.getString("security_question");
				user.answer = result.getString("security_answer");
				user.alternative = result.getString("alternative_email");
				user.latitude = result.getDouble("lat");
				user.longitude = result.getDouble("long");
				followers.add(user);
			}
			return followers;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param placeName the name of the place to be saved by the user
	 * @param email email of the user who wants to save the place
	 * @return true if operation was successful or false otherwise
	 */
	public static boolean savePlace(String placeName, String email){
		try{
			Connection connection = DBConnection.getActiveConnection();
			String sql = "INSERT INTO user_saves_place (`placeName`, `user_email`) VALUES (?, ?)";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setString(1, placeName);
			statment.setString(2, email);
			statment.executeUpdate();
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
