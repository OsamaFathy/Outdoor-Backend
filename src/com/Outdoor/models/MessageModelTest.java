package com.Outdoor.models;

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
		
	  //@Test(enabled = false)
	  @Test(dataProvider = "test1")
	  public void addMessage(String text, String user_email, String sender_user_email) {
		  Assert.assertEquals(true, messageModel.addMessage(text, user_email, sender_user_email));
	  }
}
