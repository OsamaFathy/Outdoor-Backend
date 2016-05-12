package com.Outdoor.models;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Outdoor.models.CheckinModel;

public class CheckinModelTest {

	CheckinModel checkinModel = new CheckinModel() ;
	@DataProvider(name = "test1")
	  public Object[][] dataProvider(){
		  return new Object[][] {{"test94@gmail.com", "amazing!", "FCI"}} ;
	  }
	
	@DataProvider(name = "test2")
	  public Object[][] dataProvider2(){
		  return new Object[][] {{"test94@gmail.com", 24}} ;
	  }
	
	@DataProvider(name = "test3")
	  public Object[][] dataProvider3(){
		  return new Object[][] {{23}} ;
	  }
	
	@DataProvider(name = "test4")
	  public Object[][] dataProvider4(){
		  return new Object[][] {{"ken@gmail.com", "osama.fathy@hotmail.com"}} ;
	  }
	
	//@Test(enabled = false)
	  @Test(dataProvider = "test1")
  public void addCheckin(String email, String status, String placeName) {
		  Assert.assertEquals(true, checkinModel.addCheckin(email, status, placeName));
  }

	@Test(enabled = false)
	//  @Test(dataProvider = "test2")
  public void addLike(String email, int checkin_ID) {
		  Assert.assertEquals(false, checkinModel.addLike(email, checkin_ID));
  }

	//@Test(enabled = false)
	  @Test(dataProvider = "test3")
  public void getCheckin(int checkinID) {
		  CheckinModel temp = new CheckinModel() ;
		  temp.setCheckinID(checkinID);
		  Assert.assertEquals(temp.getCheckinID(), CheckinModel.getCheckin(checkinID).getCheckinID());
  }

	@Test(enabled = false)
	  //@Test(dataProvider = "test2")
  public void getFriendsCheckins(String email) {
		ArrayList<CheckinModel> checkins = new ArrayList<>() ;
		Assert.assertEquals(checkins, CheckinModel.getFriendsCheckins(email));
  }

	@Test(enabled = false)
	//  @Test(dataProvider = "test4")
  public void getMyCheckins(String email, String email2) {
		  ArrayList<CheckinModel> checkins = new ArrayList<>() ;
			Assert.assertEquals(checkins, CheckinModel.getMyCheckins(email, email2));
  }
}
