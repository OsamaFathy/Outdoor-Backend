package com.Outdoor.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckinModel {
	int checkinID;
	String checkinUserEmail;
	String date;
	String status;
	String checkinPlaceName;
	String checkinUsername ;
	int likes;
	int likedByMe;
	
	/**
	 * This function is called when a user enters the profile of another user so we retrieve the comments of the second user
	 * and also check if the entering user likes any of these check-ins of the profile owner
	 * @param email email of the user to get check-ins 
	 * @param email2 email of the user entering the profile 
	 * @return an arrayList of check-ins or null if something failed
	 */
	public static ArrayList<CheckinModel> getMyCheckins(String email, String email2){
		try{
			ArrayList<CheckinModel> checkins = new ArrayList<>();
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM `checkin` WHERE `checkin_user_email` = ?" ;

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				CheckinModel curCheckin = new CheckinModel();
				curCheckin.checkinID = rs.getInt("checkin_id");
				curCheckin.checkinUserEmail = rs.getString("checkin_user_email");
				curCheckin.date = rs.getString("date");
				curCheckin.status = rs.getString("status");
				curCheckin.checkinPlaceName = rs.getString("checkin_place_name");
				
				
				sql = "SELECT * FROM `user_likes_checkin` WHERE `checkin_checkin_id` = ?" ;
				PreparedStatement stmt2 = conn.prepareStatement(sql,
			            ResultSet.TYPE_SCROLL_INSENSITIVE,
			            ResultSet.CONCUR_READ_ONLY);
				stmt2.setInt(1, curCheckin.checkinID);
				ResultSet rs2 = stmt2.executeQuery();
				
				if(rs2.next()){
					rs2.last();
					curCheckin.likes = rs2.getRow();
				}else{
					curCheckin.likes = 0;
				}
				
				
				sql = "SELECT * FROM `user_likes_checkin` WHERE `user_email` = ? AND `checkin_checkin_id` = ?";
				PreparedStatement stmt3 = conn.prepareStatement(sql,
			            ResultSet.TYPE_SCROLL_INSENSITIVE,
			            ResultSet.CONCUR_READ_ONLY);
				stmt3.setString(1, email2);
				stmt3.setInt(2, curCheckin.checkinID);
				ResultSet rs3 = stmt3.executeQuery();
				curCheckin.likedByMe = (rs3.next()?1:0);
				checkins.add(curCheckin);
			}
			return checkins;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get the home page which is full of check-ins of friends of the user
	 * @param email the email of the user opening his home page
	 * @return an arrayList of check-ins or null if something failed
	 */
	public static ArrayList<CheckinModel> getFriendsCheckins(String email){
		try{
			ArrayList<CheckinModel> checkins = new ArrayList<>();
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM checkin WHERE `checkin_user_email` IN "
			+ "( SELECT `friend_user_email` FROM user_has_friend WHERE `user_email` = ?)  ORDER BY date DESC" ;

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				CheckinModel curCheckin = new CheckinModel();
				curCheckin.checkinID = rs.getInt("checkin_id");
				curCheckin.checkinUserEmail = rs.getString("checkin_user_email");
				curCheckin.date = rs.getString("date");
				curCheckin.status = rs.getString("status");
				curCheckin.checkinPlaceName = rs.getString("checkin_place_name");
				curCheckin.checkinUsername = UserModel.getUserName(curCheckin.checkinUserEmail) ;
				
				sql = "SELECT * FROM `user_likes_checkin` WHERE `checkin_checkin_id` = ?" ;
				PreparedStatement stmt2 = conn.prepareStatement(sql,
			            ResultSet.TYPE_SCROLL_INSENSITIVE,
			            ResultSet.CONCUR_READ_ONLY);
				stmt2.setInt(1, curCheckin.checkinID);
				ResultSet rs2 = stmt2.executeQuery();
				
				if(rs2.next()){
					rs2.last();
					curCheckin.likes = rs2.getRow();
				}else{
					curCheckin.likes = 0;
				}
				
				
				sql = "SELECT * FROM `user_likes_checkin` WHERE `user_email` = ? AND `checkin_checkin_id` = ?";
				PreparedStatement stmt3 = conn.prepareStatement(sql,
			            ResultSet.TYPE_SCROLL_INSENSITIVE,
			            ResultSet.CONCUR_READ_ONLY);
				stmt3.setString(1, curCheckin.checkinUserEmail);
				stmt3.setInt(2, curCheckin.checkinID);
				ResultSet rs3 = stmt3.executeQuery();
				curCheckin.likedByMe = (rs3.next()?1:0);
				checkins.add(curCheckin);
			}
			return checkins;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param email email of the user who made the like or unlike
	 * @param checkin_ID the ID of the liked or unliked check-in
	 * @return true if operation was successful or false otherwise
	 */
	public static boolean addLike(String email, int checkin_ID){
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM user_likes_checkin WHERE `user_email` = ? AND `checkin_checkin_id` = ?" ;

			PreparedStatement stmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, email);
			stmt.setInt(2, checkin_ID);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				sql = "DELETE FROM user_likes_checkin WHERE `user_email` = ? AND `checkin_checkin_id` = ?" ;
				stmt = conn.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY);
				stmt.setString(1, email);
				stmt.setInt(2, checkin_ID);
				stmt.executeUpdate() ;
				return true ;
			}
			sql = "INSERT INTO user_likes_checkin (`user_email`, `checkin_checkin_id`) VALUES (?, ?)" ;
			stmt = conn.prepareStatement(sql,
			ResultSet.TYPE_SCROLL_INSENSITIVE,
	        ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, email);
			stmt.setInt(2, checkin_ID);
			stmt.executeUpdate() ;
			
			sql = "SELECT `checkin_user_email` FROM `checkin` WHERE `checkin_id` = ?" ;
			stmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, checkin_ID);
			rs = stmt.executeQuery() ;
			String owner_user_email = null ; 
			while(rs.next())
			{
				owner_user_email = rs.getString("checkin_user_email") ;
			}
			NotificationModel.addNotification("Like", email, checkin_ID, owner_user_email) ;
			return false ;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param email email of the user adding the check-in
	 * @param status the content of the check-in
	 * @param placeName the name of the place in which the user had checked-in
	 * @return true if operation was successful or false otherwise
	 */
	public static boolean addCheckin(String email, String status, String placeName){
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "INSERT INTO `checkin` (`checkin_user_email`, `date`, `status`, `checkin_place_name`) VALUES"
					+ "(?, ?, ?, ?)" ;
			Date d1 = new Date();
			SimpleDateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
			String formattedDate = df.format(d1);
			PreparedStatement stmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, email);
			stmt.setString(2, formattedDate);
			stmt.setString(3, status);
			stmt.setString(4, placeName);
			stmt.executeUpdate();
			return true ;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param checkinID the ID of the check-in we want to retrieve its comments
	 * @return an arrayList of comments or false otherwise
	 */
	public static ArrayList<CommentModel> getComments(int checkinID){
		try{
			ArrayList<CommentModel> comments = new ArrayList<>();
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM comment WHERE `checkin_id` = ?" ;
			PreparedStatement stmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, checkinID);
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
	
	/**
	 * 
	 * @param checkinID the ID of the check-in we want to retrieve
	 * @return an object of checkinModel or null if something failed
	 */
	public static CheckinModel getCheckin(int checkinID){
		CheckinModel checkin = null;
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM checkin WHERE `checkin_id` = ?" ;

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, checkinID);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				checkin = new CheckinModel();
				checkin.checkinID = rs.getInt("checkin_id");
				checkin.checkinUserEmail = rs.getString("checkin_user_email");
				checkin.date = rs.getString("date");
				checkin.status = rs.getString("status");
				checkin.checkinPlaceName = rs.getString("checkin_place_name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return checkin;
	}
	public int getCheckinID() {
		return checkinID;
	}
	public void setCheckinID(int checkin_id) {
		this.checkinID = checkin_id;
	}
	public String getCheckinUserEmail() {
		return checkinUserEmail;
	}
	public void setCheckinUserEmail(String checkin_user_email) {
		this.checkinUserEmail = checkin_user_email;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCheckinPlaceName() {
		return checkinPlaceName;
	}
	public void setCheckinPlaceName(String checkin_place_name) {
		this.checkinPlaceName = checkin_place_name;
	}
	
	public String getCheckinUsername() {
		return checkinUsername;
	}
	public void setCheckinUsername(String checkin_user_name) {
		this.checkinUsername = checkin_user_name;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getLikedByMe() {
		return likedByMe;
	}

	public void setLikedByMe(int likedByMe) {
		this.likedByMe = likedByMe;
	}
	
}
