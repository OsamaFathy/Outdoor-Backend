package com.Outdoor.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionModel {
	private String name;
	private double rate;
	private int numberOfUsers;
	private String placeUserEmail;
	private double lat;
	private double lon;
	

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
