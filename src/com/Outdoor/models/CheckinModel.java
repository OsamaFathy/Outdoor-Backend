package com.Outdoor.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckinModel {
	int checkinID;
	String checkinUserEmail;
	String date;
	String status;
	String checkinPlaceName;
	int likes;
	int likedByMe;
	

	public static ArrayList<CheckinModel> getMyCheckins(String email){
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
