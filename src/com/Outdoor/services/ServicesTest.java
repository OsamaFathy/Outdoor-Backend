package com.Outdoor.services;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ServicesTest {
  
  Services services = new Services() ;	
	
  @DataProvider(name = "test1")
  public Object[][] dataProvider(){
	  return new Object[][] {{"Hello!", "test94@gmail.com", "osama.fathy@hotmail.com"}} ;
  }	
	
  //@Test(enabled = false)
  @Test(dataProvider = "test1")
  public void addMessage(String text, String user_email, String sender_user_email) {
	  JSONObject json = new JSONObject();
	  json.put("success", 1);
	  Assert.assertEquals(json.toJSONString(), services.addMessage(text, user_email, sender_user_email));
  }
  
  //@Test(enabled = false)
  /*@Test
  public void addNotification() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void addPlace() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void checkin() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void commentToBrand() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void commentToCheckin() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void commentToPlace() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void follow() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void getAllMessages() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void getAllNotifications() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void getCheckinComments() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void getFollowers() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void getFriendsCheckins() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void getJson() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void getLastPosition() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void getNearestLocation() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void getPlace() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void getPlaceComments() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void getProfile() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void like() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void login() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void ratePlace() {
    throw new RuntimeException("Test not implemented");
  }
  
  //@Test(enabled = false)
  @Test
  public void savePlace() {
    throw new RuntimeException("Test not implemented");
  }

  //@Test(enabled = false)
  @Test
  public void signUp() {
    throw new RuntimeException("Test not implemented");
  }

  //@Test(enabled = false)
  @Test
  public void unfollow() {
    throw new RuntimeException("Test not implemented");
  }

  //@Test(enabled = false)
  @Test
  public void updatePosition() {
    throw new RuntimeException("Test not implemented");
  }*/
}
