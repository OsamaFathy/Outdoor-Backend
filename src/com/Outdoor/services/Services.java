package com.Outdoor.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;

import com.Outdoor.models.CheckinModel;
import com.Outdoor.models.DBConnection;
import com.Outdoor.models.PositionModel;
import com.Outdoor.models.UserModel;

@Path("/")
public class Services {

	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */

	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("email") String email, @FormParam("username") String name,
			@FormParam("password") String pass, @FormParam("securityQuestion") String question, @FormParam("securityAnswer") String ans,
			@FormParam("altEmail") String alt) {

		//JSONObject json1 = new JSONObject();
		UserModel user = UserModel.addNewUser(name, email, pass, question, ans, alt);
		JSONObject json = new JSONObject();
		if (user != null) {
			json.put("success", 1);
			json.put("email", user.getEmail());
			json.put("name", user.getName());
			json.put("password", user.getPass());
			json.put("question", user.getQuestion());
			json.put("answer", user.getAnswer());
			json.put("alternative", user.getAlternative());
			json.put("lat", user.getLat());
			json.put("long", user.getLon());
		} else {
			json.put("status", 0);
		}
		return json.toJSONString();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("loginEmail") String email, @FormParam("loginPassword") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		if (user != null) {
			json.put("success", 1);
			json.put("email", user.getEmail());
			json.put("name", user.getName());
			json.put("password", user.getPass());
			json.put("question", user.getQuestion());
			json.put("answer", user.getAnswer());
			json.put("alternative", user.getAlternative());
			json.put("lat", user.getLat());
			json.put("long", user.getLon());
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}

	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("email") String email,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		
		Boolean status = UserModel.updateUserPosition(email, Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	@POST
	@Path("/follow")
	@Produces(MediaType.TEXT_PLAIN)
	public String follow(@FormParam("email") String email,
			@FormParam("friendEmail") String friendEmail) {
		Boolean status = UserModel.followFriend(email, friendEmail) ;
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	@POST
	@Path("/unfollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String unfollow(@FormParam("email") String email,
			@FormParam("friendEmail") String friendEmail) {
		Boolean status = UserModel.unfollowFriend(email, friendEmail) ;
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}


	@POST
	@Path("/getLastPosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLastPosition(@FormParam("email") String email) {
		
		PositionModel pos = UserModel.getLastPosition(email);
		JSONObject json = new JSONObject();
		if(pos != null){
			json.put("status", 1);
			json.put("lat", pos.getLat());
			json.put("long", pos.getLon());
			
		}else{
			json.put("status", 0);
		}
		
		return json.toJSONString();
	}
	
	@POST
	@Path("/getFollowers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFollowers(@FormParam("email") String email) {
		ArrayList<UserModel> users = UserModel.getFollowers(email);
		JSONObject json = new JSONObject();
		if (users != null) {
			json.put("status", 1);

			int ind = 0;
			for(UserModel user : users){
				JSONObject cur = new JSONObject();
				cur.put("email", user.getEmail());
				cur.put("name", user.getName());
				cur.put("password", user.getPass());
				cur.put("question", user.getQuestion());
				cur.put("answer", user.getAnswer());
				cur.put("alternative", user.getAlternative());
				cur.put("lat", user.getLat());
				cur.put("long", user.getLon());
				json.put(ind++, cur);
			}
			return json.toJSONString();
		}else{
			json.put("status", 0);
		}
		return json.toJSONString();
	}

	
	@POST
	@Path("/getProfile")
	@Produces(MediaType.TEXT_PLAIN)
	public String getProfile(@FormParam("my_email") String myEmail,@FormParam("his_email") String hisEmail) {
		ArrayList<CheckinModel> checkins = CheckinModel.getMyCheckins(hisEmail);
		JSONObject json = new JSONObject();
		if (checkins != null) {
			json.put("success", 1);
			
			JSONObject JSCheckins = new JSONObject();
			int ind = 0;
			for(CheckinModel checkin : checkins){
				JSONObject cur = new JSONObject();
				
				cur.put("checkin_id", checkin.getCheckinID());
				cur.put("checkin_user_email", checkin.getCheckinUserEmail());
				cur.put("date", checkin.getDate());
				cur.put("status", checkin.getStatus());
				cur.put("checkin_place_name", checkin.getCheckinPlaceName());
				cur.put("likes", checkin.getLikes());
				cur.put("if_liked", checkin.getLikedByMe());
			
				JSCheckins.put(ind++, cur);
			}
			String user = UserModel.getUserName(hisEmail);
			boolean followed = UserModel.Followed(myEmail, hisEmail);
			json.put("username", user);
			json.put("is_friend", (followed?1:0));
			json.put("array", JSCheckins);
			return json.toJSONString();
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}

	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
}
