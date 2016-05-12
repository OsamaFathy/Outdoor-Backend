package com.Outdoor.models;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NotificationModelTest {

	NotificationModel notificationModel = new NotificationModel() ;
	
	@DataProvider(name = "test1")
	  public Object[][] numberProvider(){
		  return new Object[][] {{"Like", "test94@gmail.com", 1, "osama.fathy@hotmail.com"}} ;
		  }
	
	@DataProvider(name = "test2")
	  public Object[][] numberProvider2(){
		  return new Object[][] {{"test94@gmail.com"}} ;
		  }
	
	@Test(enabled = false)
	  //@Test(dataProvider = "test1")
  public void addNotification(String type, String user_email, int checkinID, String owner_user_email) {
	  Assert.assertEquals(true, notificationModel.addNotification(type, user_email, checkinID, owner_user_email));
  }

@Test(enabled = false)
  //@Test(dataProvider = "test2")
  public void getMyNotifications(String email) {
	  ArrayList<NotificationModel> notifications = new ArrayList<>() ;
	  NotificationModel notification = new NotificationModel() ;
	  notification.setType("Like");
	  notification.setOwner_user_email("test94@gmail.com");
	  notification.setCheckinID(1);
	  notification.setOwner_user_email("osama.fathy@hotmail.com");
	  Assert.assertEquals(true, notificationModel.getMyNotifications(email));
  }
}
