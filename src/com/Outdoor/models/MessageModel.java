package com.Outdoor.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageModel {
	private String text, user_email, sender_user_email ;
	private int messageID ;
	private Date date ;
	
	/**
	 * 
	 * @param text content of the message
	 * @param user_email the receiver email
	 * @param sender_user_email the sender email
	 * @return true if sent successfully or false otherwise
	 */
	public static boolean addMessage(String text, String user_email, String sender_user_email){
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "INSERT INTO user_has_message (`user_email`, `sender_user_email`, `message`, `date`) "
					+ "VALUES (?, ?, ?, ?)" ;
			
			Date d1 = new Date();
			SimpleDateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
			String formattedDate = df.format(d1);
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user_email);
			stmt.setString(2, sender_user_email);
			stmt.setString(3, text);
			stmt.setString(4, formattedDate);
			
			stmt.executeUpdate() ;
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param email the email of the user whose messages would be retrieved
	 * @return an arrayList of messages or null if something failed
	 */
	public static ArrayList<MessageModel> getMyMessages(String email){
		try{
			ArrayList<MessageModel> messages = new ArrayList<>();
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM `user_has_message` WHERE `user_email` = ?" ;

			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				MessageModel message = new MessageModel();
				message.setMessageID(rs.getInt("user_has_message_id"));
				message.setUser_email(rs.getString("user_email"));
				message.setSender_user_email(rs.getString("sender_user_email"));
				message.setDate(rs.getDate("date"));
				message.setText(rs.getString("message"));
				messages.add(message);
			}
			return messages;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getSender_user_email() {
		return sender_user_email;
	}

	public void setSender_user_email(String sender_user_email) {
		this.sender_user_email = sender_user_email;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
