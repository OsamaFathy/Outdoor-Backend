package com.Outdoor.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MessageModelTest {
	
	MessageModel messageModel = new MessageModel() ;
	
	@DataProvider(name = "test1")
	  public Object[][] dataProvider(){
		  return new Object[][] {{"Hello!", "test94@gmail.com", "osama.fathy@hotmail.com"}} ;
	  }
	
	@DataProvider(name = "test2")
	  public Object[][] dataProvider2(){
		  return new Object[][] {{"ken@gmail.com"}} ;
	  }
		
	  //@Test(enabled = false)
	  @Test(dataProvider = "test1")
	  public void addMessage(String text, String user_email, String sender_user_email) {
		  Assert.assertEquals(true, messageModel.addMessage(text, user_email, sender_user_email));
	  }
	  
	//@Test(enabled = false)
	  @Test(dataProvider = "test2")
	  public void getMyMessages(String user_email) {
		  MessageModel message = new MessageModel() ;
		  message.setText("OK");
		  message.setMessageID(26);
		  message.setSender_user_email("osama.fathy@hotmail.com");
		  message.setUser_email(user_email);
		  Assert.assertEquals(message.getMessageID(), messageModel.getMyMessages(user_email).get(0).getMessageID());
	  }
}
