package com.Outdoor.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentModel {
	private String text ;
	private String email ;
	private Date date;
	public static boolean addCommentToCheckin(String text, String email, int checkinID){
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "INSERT INTO comment (`text`, `comment_user_email`, `date`, `checkin_id`) VALUES"
					+ "(?, ?, ?, ?)" ;
			Date d1 = new Date();
			SimpleDateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
			String formattedDate = df.format(d1);
			PreparedStatement stmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, text);
			stmt.setString(2, email);
			stmt.setString(3, formattedDate);
			stmt.setInt(4, checkinID);
			stmt.executeUpdate();
			
			sql = "SELECT `checkin_user_email` FROM `checkin` WHERE `checkin_id` = ?" ;
			stmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, checkinID);
			ResultSet rs = stmt.executeQuery() ;
			String owner_user_email = null ; 
			while(rs.next())
			{
				owner_user_email = rs.getString("checkin_user_email") ;
			}
			NotificationModel.addNotification("Comment", email, checkinID, owner_user_email) ;
			return true ;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean addCommentToPlace(String text, String email, String placeName){
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "INSERT INTO comment (`text`, `comment_user_email`, `date`, `placeName`) VALUES"
					+ "(?, ?, ?, ?)" ;
			Date d1 = new Date();
			SimpleDateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
			String formattedDate = df.format(d1);
			PreparedStatement stmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, text);
			stmt.setString(2, email);
			stmt.setString(3, formattedDate);
			stmt.setString(4, placeName);
			stmt.executeUpdate();
			return true ;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean addCommentToBrand(String text, String email, int brandID){
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "INSERT INTO comment (`text`, `comment_user_email`, `date`, `brand_id`) VALUES"
					+ "(?, ?, ?, ?)" ;
			Date d1 = new Date();
			SimpleDateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
			String formattedDate = df.format(d1);
			PreparedStatement stmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, text);
			stmt.setString(2, email);
			stmt.setString(3, formattedDate);
			stmt.setInt(4, brandID);
			stmt.executeUpdate();
			return true ;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
