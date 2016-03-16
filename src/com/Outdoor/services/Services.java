package com.Outdoor.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	public String signUp(@FormParam("email") String email, @FormParam("name") String name,
			@FormParam("password") String pass, @FormParam("question") String question, @FormParam("answer") String ans,
			@FormParam("alternative") String alt) {

		//JSONObject json1 = new JSONObject();
		UserModel user = UserModel.addNewUser(name, email, pass, question, ans, alt);
		JSONObject json = new JSONObject();
		if (user != null) {
			json.put("status", 1);
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
	public String login(@FormParam("email") String email, @FormParam("password") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		if (user != null) {
			json.put("status", 1);
			json.put("email", user.getEmail());
			json.put("name", user.getName());
			json.put("password", user.getPass());
			json.put("question", user.getQuestion());
			json.put("answer", user.getAnswer());
			json.put("alternative", user.getAlternative());
			json.put("lat", user.getLat());
			json.put("long", user.getLon());
		}else{
			json.put("status", 0);
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

	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
}
