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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Outdoor.models.CheckinModel;
import com.Outdoor.models.CommentModel;
import com.Outdoor.models.DBConnection;
import com.Outdoor.models.MessageModel;
import com.Outdoor.models.NotificationModel;
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
		JSONArray JSUsers = new JSONArray();
		if (users != null) {
			json.put("success", 1);

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
				JSUsers.add(cur);
			}
			json.put("array", JSUsers);
		
			
			return json.toJSONString();
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}

	
	@POST
	@Path("/getProfile")
	@Produces(MediaType.TEXT_PLAIN)
	public String getProfile(@FormParam("my_email") String myEmail,@FormParam("his_email") String hisEmail) {
		ArrayList<CheckinModel> checkins = CheckinModel.getMyCheckins(hisEmail, myEmail);
		JSONObject json = new JSONObject();
		if (checkins != null) {
			json.put("success", 1);
			
			JSONArray JSCheckins = new JSONArray();
			
			for(CheckinModel checkin : checkins){
				JSONObject cur = new JSONObject();		
				cur.put("checkin_id", checkin.getCheckinID());
				cur.put("checkin_user_email", checkin.getCheckinUserEmail());
				cur.put("date", checkin.getDate());
				cur.put("status", checkin.getStatus());
				cur.put("checkin_place_name", checkin.getCheckinPlaceName());
				cur.put("likes", checkin.getLikes());
				cur.put("if_liked", checkin.getLikedByMe());
				ArrayList<CommentModel> comments = checkin.getComments(checkin.getCheckinID()) ;
				cur.put("numOfComments", comments.size()) ;
				/*JSONArray JSComments = new JSONArray() ;
				ArrayList<CommentModel> comments = checkin.getComments(checkin.getCheckinID()) ;
				for(CommentModel comment:comments)
				{
					JSONObject cur2 = new JSONObject() ;
					cur2.put("text", comment.getText()) ;
					cur2.put("date", comment.getDate()) ;
					cur2.put("username", UserModel.getUserName(comment.getEmail())) ;
					JSComments.add(cur2) ;
				}
				cur.put("comments", JSComments);*/
			
				JSCheckins.add(cur);
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


	@POST
	@Path("/getNearestLocation")
	@Produces(MediaType.TEXT_PLAIN)
	public String getNearestLocation(@FormParam("lat") Double lat,@FormParam("long") Double lon) {
		PositionModel pos = PositionModel.getNearestLocation(lat, lon);
		JSONObject json = new JSONObject();
		if(pos != null){
			json.put("success", 1);
			json.put("name", pos.getName());
			json.put("rate", pos.getRate());
			json.put("numberOfUsers", pos.getNumberOfUsers());
			json.put("placeUserEmail", pos.getPlaceUserEmail());
			json.put("lat", pos.getLat());
			json.put("long", pos.getLon());	
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	

	@POST
	@Path("/getPlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPlace(@FormParam("email") String email, @FormParam("placeName") String name) {
		PositionModel pos = PositionModel.getPlace(name);
		double rate = PositionModel.getMyRating(email, name);
		JSONObject json = new JSONObject();
		if(pos != null){
			json.put("success", 1);
			json.put("name", pos.getName());
			json.put("rate", pos.getRate());
			json.put("numberOfUsers", pos.getNumberOfUsers());
			json.put("placeUserEmail", pos.getPlaceUserEmail());
			json.put("lat", pos.getLat());
			json.put("long", pos.getLon());
			json.put("myRate", rate);	
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
		
	}

	@POST
	@Path("/ratePlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String ratePlace(@FormParam("email") String email, @FormParam("placeName") String name, @FormParam("rate") Double rate) {
		boolean done = PositionModel.ratePlace(email, name, rate);
		JSONObject json = new JSONObject();
		if(done){
			json.put("success", 1);
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}

	@POST
	@Path("/like")
	@Produces(MediaType.TEXT_PLAIN)
	public String like(@FormParam("email") String email, @FormParam("checkin_id") int checkinID) {
		boolean isLiked = CheckinModel.addLike(email, checkinID) ;
		JSONObject json = new JSONObject();
		if (isLiked == true) {
			json.put("success", 0);
		}else{
			json.put("success", 1);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/checkin")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkin(@FormParam("email") String email, @FormParam("placeName") String placeName, 
			@FormParam("status") String status) {
		boolean exists = PositionModel.placeFound(placeName) ;
		JSONObject json = new JSONObject();
		if (exists == true) {
			boolean operation = CheckinModel.addCheckin(email, status, placeName) ;
			if(operation==true)
				json.put("success", 1);
			else
				json.put("success", 0) ;
		}else{
			json.put("success", 0);
			json.put("message", "Can't find such a place") ;
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/addPlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPlace(@FormParam("placeName") String placeName, @FormParam("email") String email,
			@FormParam("lon") double lon, @FormParam("lat") double lat) {
		boolean operation = PositionModel.addPlace(placeName, 0.0, 0, email, lon, lat) ;
		JSONObject json = new JSONObject();
		if (operation == true) {
			json.put("success", 1) ;
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/savePlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String savePlace(@FormParam("placeName") String placeName, @FormParam("email") String email) {
		boolean operation = UserModel.savePlace(placeName, email) ;
		JSONObject json = new JSONObject();
		if (operation == true) {
			json.put("success", 1) ;
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/commentToCheckin")
	@Produces(MediaType.TEXT_PLAIN)
	public String commentToCheckin(@FormParam("text") String text, @FormParam("email") String email, 
			@FormParam("checkin_id") int checkinID) {
		boolean operation = CommentModel.addCommentToCheckin(text, email, checkinID) ;
		JSONObject json = new JSONObject();
		if (operation == true) {
			json.put("success", 1) ;
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/commentToPlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String commentToPlace(@FormParam("text") String text, @FormParam("email") String email, 
			@FormParam("placeName") String placeName) {
		boolean operation = CommentModel.addCommentToPlace(text, email, placeName) ;
		JSONObject json = new JSONObject();
		if (operation == true) {
			json.put("success", 1) ;
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/commentToBrand")
	@Produces(MediaType.TEXT_PLAIN)
	public String commentToBrand(@FormParam("text") String text, @FormParam("email") String email, 
			@FormParam("checkin_id") int brandID) {
		boolean operation = CommentModel.addCommentToBrand(text, email, brandID) ;
		JSONObject json = new JSONObject();
		if (operation == true) {
			json.put("success", 1) ;
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/getCheckinComments")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCheckinComments(@FormParam("checkin_id") int checkinID) {
		ArrayList<CommentModel> comments = CheckinModel.getComments(checkinID);
		JSONObject json = new JSONObject();
		JSONArray JSUsers = new JSONArray();
		if (comments != null) {
			json.put("success", 1);

			for(CommentModel comment : comments){
				JSONObject cur = new JSONObject();
				cur.put("text", comment.getText());
				cur.put("username", UserModel.getUserName(comment.getEmail()));
				cur.put("date", comment.getDate());
				JSUsers.add(cur);
			}
			json.put("array", JSUsers);
		
			
			return json.toJSONString();
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/getFriendsCheckins")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFriendsCheckins(@FormParam("email") String email) {
		ArrayList<CheckinModel> checkins = CheckinModel.getFriendsCheckins(email);
		JSONObject json = new JSONObject();
		if (checkins != null) {
			json.put("success", 1);
			
			JSONArray JSCheckins = new JSONArray();
			
			for(CheckinModel checkin : checkins){
				JSONObject cur = new JSONObject();		
				cur.put("checkin_id", checkin.getCheckinID());
				cur.put("checkin_user_email", checkin.getCheckinUserEmail());
				cur.put("checkin_user_name", checkin.getCheckinUsername()) ;
				cur.put("date", checkin.getDate());
				cur.put("status", checkin.getStatus());
				cur.put("checkin_place_name", checkin.getCheckinPlaceName());
				cur.put("likes", checkin.getLikes());
				cur.put("if_liked", checkin.getLikedByMe());
				ArrayList<CommentModel> comments = checkin.getComments(checkin.getCheckinID()) ;
				cur.put("numOfComments", comments.size()) ;
				/*JSONArray JSComments = new JSONArray() ;
				ArrayList<CommentModel> comments = checkin.getComments(checkin.getCheckinID()) ;
				for(CommentModel comment:comments)
				{
					JSONObject cur2 = new JSONObject() ;
					cur2.put("text", comment.getText()) ;
					cur2.put("date", comment.getDate()) ;
					cur2.put("username", UserModel.getUserName(comment.getEmail())) ;
					JSComments.add(cur2) ;
				}
				cur.put("comments", JSComments);*/
			
				JSCheckins.add(cur);
			}
			json.put("array", JSCheckins);
			return json.toJSONString();
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/getAllNotifications")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllNotifications(@FormParam("email") String email) {
		ArrayList<NotificationModel> notifications = NotificationModel.getMyNotifications(email);
		JSONObject json = new JSONObject();
		if (notifications != null) {
			json.put("success", 1);
			
			JSONArray JSCheckins = new JSONArray();
			
			for(NotificationModel notification : notifications){
				JSONObject cur = new JSONObject();		
				cur.put("notification_id", notification.getNotificationID());
				cur.put("type", notification.getType());
				cur.put("username", UserModel.getUserName(notification.getUser_email()));
				cur.put("status", notification.getStatus());
				JSCheckins.add(cur);
			}
			json.put("array", JSCheckins);
			return json.toJSONString();
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/addNotification")
	@Produces(MediaType.TEXT_PLAIN)
	public String addNotification(@FormParam("type") String email, @FormParam("user_email") String user_email
			, @FormParam("checkin_id") int checkinID, @FormParam("user_email") String owner_user_email) {
		boolean operation = NotificationModel.addNotification(email, user_email, checkinID, owner_user_email);
		JSONObject json = new JSONObject();
		if (operation == true) {
			json.put("success", 1);
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/addMessage")
	@Produces(MediaType.TEXT_PLAIN)
	public String addMessage(@FormParam("text") String text, @FormParam("user_email") String user_email
			, @FormParam("sender_user_email") String sender_user_email) {
		boolean operation = MessageModel.addMessage(text, user_email, sender_user_email);
		JSONObject json = new JSONObject();
		if (operation == true) {
			json.put("success", 1);
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/getAllMessages")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllMessages(@FormParam("email") String email) {
		ArrayList<MessageModel> messages = MessageModel.getMyMessages(email);
		JSONObject json = new JSONObject();
		if (messages != null) {
			json.put("success", 1);
			
			JSONArray JSCheckins = new JSONArray();
			
			for(MessageModel message : messages){
				String sender = UserModel.getUserName(message.getSender_user_email());
				
				
				JSONObject cur = new JSONObject();
				
				cur.put("message_id", message.getMessageID());
				cur.put("text", message.getText());
				cur.put("user_email", message.getUser_email());
				cur.put("sender_user_email", message.getSender_user_email());
				cur.put("sender_user_name", sender);
				cur.put("date", message.getDate()) ;
				JSCheckins.add(cur);
			}
			json.put("array", JSCheckins);
			return json.toJSONString();
		}else{
			json.put("success", 0);
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/getPlaceComments")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPlaceComments(@FormParam("placeName") String placeName) {
		ArrayList<CommentModel> comments = PositionModel.getComments(placeName);
		JSONObject json = new JSONObject();
		JSONArray JSUsers = new JSONArray();
		if (comments != null) {
			json.put("success", 1);

			for(CommentModel comment : comments){
				JSONObject cur = new JSONObject();
				cur.put("text", comment.getText());
				cur.put("user_email", comment.getEmail());
				cur.put("user_name", comment.getUsername()) ;
				cur.put("date", comment.getDate());
				JSUsers.add(cur);
			}
			json.put("array", JSUsers);
		
			
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
