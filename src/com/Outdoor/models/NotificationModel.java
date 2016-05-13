package com.Outdoor.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class NotificationModel {
	private int notificationID ;
	private String type, user_email, owner_user_email, status;
	private int checkinID ;
	
	/**
	 * 
	 * @param type the notification type like or comment
	 * @param user_email the user who invoked the like or comment
	 * @param checkinID the id of the check-in on which the user liked or commented
	 * @param owner_user_email the owner of the check-in i.e. the notification would go to this user
	 * @return true if added successfully or false otherwise
	 */
	public static boolean addNotification(String type, String user_email, int checkinID, String owner_user_email){
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "INSERT INTO notification (`type`, `user_email`, `checkin_id`, `owner_user_email`) VALUES"
					+ "(?, ?, ?, ?)" ;

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, type);
			stmt.setString(2, user_email);
			stmt.setInt(3, checkinID);
			stmt.setString(4, owner_user_email);
			
			stmt.executeUpdate() ;
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param email the email of the user whose notifications would be retrieved 
	 * @return an arrayList of notifications or null if something failed
	 */
	public static ArrayList<NotificationModel> getMyNotifications(String email){
		try{
			ArrayList<NotificationModel> notifications = new ArrayList<>();
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM notification WHERE `owner_user_email` = ? ORDER BY `notification_id` DESC" ;

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				NotificationModel notification = new NotificationModel();
				notification.setNotificationID(rs.getInt("notification_id"));
				notification.setType(rs.getString("type"));
				notification.setUser_email(rs.getString("user_email"));
				notification.setCheckinID(rs.getInt("checkin_id")) ;
				String text = CheckinModel.getCheckin(notification.getCheckinID()).getStatus();
				notification.setStatus(text.substring(0, Math.min(30, text.length())));
				notification.setOwner_user_email(rs.getString("owner_user_email"));
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getOwner_user_email() {
		return owner_user_email;
	}

	public void setOwner_user_email(String owner_user_email) {
		this.owner_user_email = owner_user_email;
	}

	public int getCheckinID() {
		return checkinID;
	}

	public void setCheckinID(int checkinID) {
		this.checkinID = checkinID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
