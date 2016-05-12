package com.Outdoor.models;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserModelTest {

	UserModel userModel = new UserModel() ;
	
	@DataProvider(name = "test1")
	  public Object[][] dataProvider(){
		  return new Object[][] {{"test94@gmail.com", "osama.fathy@hotmail.com"}} ;
	  }
	
	@DataProvider(name = "test2")
	  public Object[][] dataProvider2(){
		  return new Object[][] {{"newUser@new.com", "new", "1234", "Do u have iron?", "u r cheaper or ezz el dekheila", 
			  "bess@bessYaMan.com"}} ;
	  }
	
	@DataProvider(name = "test3")
	  public Object[][] dataProvider3(){
		  return new Object[][] {{"test94@gmail.com", "1234"}} ;
	  }
	
	@DataProvider(name = "test4")
	  public Object[][] dataProvider4(){
		  return new Object[][] {{"3ndy fel beet", "test94@gmail.com"}} ;
	  }
	
	@DataProvider(name = "test5")
	  public Object[][] dataProvider5(){
		  return new Object[][] {{"test@gmail.com", "51.23", "94.94"}} ;
	  }
	
	//@Test(enabled = false)
	  @Test(dataProvider = "test1")
  public void Followed(String user_email, String sender_user_email) {
		  Assert.assertEquals(true, userModel.Followed(user_email, sender_user_email));
  }

	//@Test(enabled = false)
	  @Test(dataProvider = "test2")
  public void addNewUser(String name, String email, String pass, String question, String ans, String alternative) {
    UserModel user = new UserModel() ;
    user.setEmail(email);
    user.setName(name);
    user.setPass(pass);
    user.setQuestion(question);
    user.setAnswer(ans);
    user.setAlternative(alternative);
    user.setLon(0.0);
    user.setLat(0.0);
    Assert.assertEquals(true, userModel.addNewUser(name, email, pass, question, ans, alternative));
  }

	//@Test(enabled = false)
	  @Test(dataProvider = "test1")
  public void followFriend(String email, String friendEmail) {
		  Assert.assertEquals(true, userModel.followFriend(email, friendEmail));
  }

	//@Test(enabled = false)
	  @Test(dataProvider = "test3")
  public void login(String email, String pass) {
    UserModel user = new UserModel() ;
    user.setEmail(email);
    user.setPass(pass);
    Assert.assertEquals(true, userModel.login(email, pass));
  }

	//@Test(enabled = false)
	  @Test(dataProvider = "test4")
  public void savePlace(String placeName, String email) {
		  Assert.assertEquals(true, userModel.savePlace(placeName, email));
  }

	//@Test(enabled = false)
	  @Test(dataProvider = "test1")
  public void unfollowFriend(String user_email, String sender_user_email) {
	  Assert.assertEquals(true, userModel.unfollowFriend(user_email, sender_user_email));
  }

	//@Test(enabled = false)
	  @Test(dataProvider = "test5")
  public void updateUserPosition(String email, Double lat, Double lon) {
	  Assert.assertEquals(true, userModel.updateUserPosition(email, lat, lon));
  }
}
