package com.Outdoor.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class NotificationModel {
	private int notificationID ;
	private String text, action ;
	String date ;
	
	public static ArrayList<NotificationModel> getMyNotifications(String email){
		try{
			ArrayList<NotificationModel> notifications = new ArrayList<>();
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM notification WHERE `notification_user_email` = ?" ;

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				NotificationModel notification = new NotificationModel();
				notification.setNotificationID(rs.getInt("notification_id"));
				notification.date = rs.getString("date");
				notification.setText(rs.getString("text"));
				notification.setAction(rs.getString("action"));
				notifications.add(notification);
			}
			return notifications;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public int getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
