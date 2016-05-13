package com.Outdoor.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class PositionModel {
	private String name;
	private double rate;
	private int numberOfUsers;
	private String placeUserEmail;
	private double lat;
	private double lon;
	
	/**
	 * This functions gets the nearest place to the current place of the user given by his longitude and latitude
	 * @param lat latitude of the user
	 * @param lon longitude of the user
	 * @return the nearest position
	 */
	public static PositionModel getNearestLocation(Double lat, Double lon) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM  `place` INNER JOIN  `location` WHERE place.location_id = location.location_id ORDER BY ( ? -  `latitude` ) * ( ? -  `latitude` ) + ( ? -  `longitude` ) * ( ? -  `longitude` ) LIMIT 0 , 1";
					
			
			PreparedStatement stmt;
			System.out.println(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lat);
			stmt.setDouble(3, lon);
			stmt.setDouble(4, lon);
			System.out.println(stmt.toString());
			PositionModel pos = new PositionModel();
			
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				
				pos.name = rs.getString("placeName");
				pos.rate = rs.getDouble("rate");
				pos.numberOfUsers = rs.getInt("numberOfUsers");
				pos.placeUserEmail = rs.getString("place_user_email");
				pos.setLat(rs.getDouble("latitude"));
				pos.setLon(rs.getDouble("longitude"));
				return pos;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * gets the place whose name is given
	 * @param placeName the name of the place we want to get
	 * @return an object of positionModel class whose name is given in the parameter
	 */
	public static PositionModel getPlace(String placeName) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM `place` WHERE `placeName` = ?";
			
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, placeName);
			System.out.println(sql);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				PositionModel pos = new PositionModel();
				pos.name = rs.getString("placeName");
				pos.rate = rs.getDouble("rate");
				pos.numberOfUsers = rs.getInt("numberOfUsers");
				pos.placeUserEmail = rs.getString("place_user_email");
				return pos;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param email the email of the user who gives the rate
	 * @param placeName the name of the place to be rated
	 * @param rate the rate given by the user
	 * @return true if operation done successfully or false otherwise
	 */
	public static boolean ratePlace(String email, String placeName, double rate) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "INSERT INTO `user_rated_place` (`user_email`, `place_name`, `rating`) VALUES (?, ?, ?)";
			
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, placeName);
			stmt.setDouble(3, rate);
			stmt.executeUpdate();
			
			
			PositionModel place = getPlace(placeName);
			double newRate = (place.rate*place.numberOfUsers + rate)/(place.numberOfUsers+1);
			int newVoters = (place.numberOfUsers+1);
			sql = "UPDATE  `place` SET  `rate` =  ?, `numberOfUsers` =  ? WHERE  `placeName` = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, newRate);
			stmt.setInt(2, newVoters);
			stmt.setString(3, place.name);
			stmt.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param email the email of the user
	 * @param placeName the place of the rated place
	 * @return the rating given by the given user to the given place
	 */
	public static Double getMyRating(String email, String placeName) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT `rating` FROM `user_rated_place` WHERE `user_email` = ? AND `place_name` = ?";
			
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, placeName);
			System.out.println(sql);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getDouble("rating");
			}else
				return -1.0 ;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1.0;
	}
	
	/**
	 * 
	 * @param placeName name of the place we want to find in our system
	 * @return true if found false otherwise
	 */
	public static boolean placeFound(String placeName) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM place WHERE placeName = ?";
					
				
			PreparedStatement stmt;
			System.out.println(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, placeName);
			
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true ;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}	
	/**
	 * 
	 * @param placeName the name of the new place
	 * @param rate the rating of the place
	 * @param numberOfUsers number of user visited this place before
	 * @param email the email of the user who created this place
	 * @param lon the longitude of the new place
	 * @param lat the latitude of the new place
	 * @return true of added successfully or false otherwise
	 */
	public static boolean addPlace(String placeName, double rate, int numberOfUsers, String email, double lon, double lat) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "INSERT INTO location (`longitude`, `latitude`) VALUES (?, ?)";
			
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);			
			stmt.setDouble(1, lon);
			stmt.setDouble(2, lat);
			stmt.executeUpdate();
			
			int locationID = 0 ;
			sql = "SELECT `location_id` FROM location WHERE `longitude` = ? AND `latitude` = ?" ;
			stmt = conn.prepareStatement(sql);			
			stmt.setDouble(1, lon);
			stmt.setDouble(2, lat);
			ResultSet rs = stmt.executeQuery() ;
			if (rs.next()) {
				locationID = rs.getInt(1) ;
			}
			sql = "INSERT INTO place (`placeName`, `rate`, `numberOfUsers`, `place_user_email`, `location_id`) VALUES "
					+ "(?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql, 
					ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, placeName);
			stmt.setDouble(2, rate);
			stmt.setInt(3, numberOfUsers);
			stmt.setString(4, email);
			stmt.setInt(5, locationID);
			stmt.executeUpdate();
			return true ;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param placeName the name of the place we want to retrieve comments of the users on it
	 * @return an arrayList of comments on this place or null of something failed
	 */
	public static ArrayList<CommentModel> getComments(String placeName){
		try{
			ArrayList<CommentModel> comments = new ArrayList<>();
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM comment WHERE `placeName` = ? ORDER BY date DESC" ;
			PreparedStatement stmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, placeName);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				CommentModel comment = new CommentModel();
				comment.setText(rs.getString("text"));
				comment.setEmail(rs.getString("comment_user_email"));
				comment.setUsername(UserModel.getUserName(comment.getEmail()));
				comment.setDate(rs.getDate("date"));
				comments.add(comment);
			}
			return comments;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null ;
	}
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getRate() {
		return rate;
	}


	public void setRate(double rate) {
		this.rate = rate;
	}


	public int getNumberOfUsers() {
		return numberOfUsers;
	}


	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}


	public String getPlaceUserEmail() {
		return placeUserEmail;
	}


	public void setPlaceUserEmail(String placeUserEmail) {
		this.placeUserEmail = placeUserEmail;
	}
	
}
