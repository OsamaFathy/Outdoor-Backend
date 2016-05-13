package com.Outdoor.models;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PositionModelTest {

	PositionModel positionModel = new PositionModel() ;
	@DataProvider(name = "test1")
	  public Object[][] numberProvider(){
		  return new Object[][] {{"Egg square", 9.1, 500, "test94@gmail.com", 1.2, 6.37}} ;
		  }
	
	@DataProvider(name = "test2")
	  public Object[][] numberProvider2(){
		  return new Object[][] {{"Egg street"}} ;
		  }
	
	@DataProvider(name = "test3")
	  public Object[][] numberProvider3(){
		  return new Object[][] {{"test94@gmail.com", "Giza"}} ;
		  }
	
	@DataProvider(name = "test4")
	  public Object[][] numberProvider4(){
		  return new Object[][] {{10.10, 6.6}} ;
		  }
	
	@DataProvider(name = "test5")
	  public Object[][] numberProvider5(){
		  return new Object[][] {{"hassan.gamal@Jelena.com", "Serbia", 10.0}} ;
		  }
	
	@Test(enabled = false)
	//  @Test(dataProvider = "test1")
  public void addPlace(String placeName, double rate, int numberOfUsers, String email, double lon, double lat) {
	  Assert.assertEquals(true, positionModel.addPlace(placeName, rate, numberOfUsers, email, lon, lat));
  }

	@Test(enabled = false)
	//  @Test(dataProvider = "test2")
  public void getComments(String placeName) {
	  CommentModel comment = new CommentModel() ;
	  comment.setEmail("osama.fathy@hotmail");
	  comment.setText("Nice place");
	  comment.setUsername("osama");
	  ArrayList<CommentModel> comments = new ArrayList<>() ;
	  comments.add(comment) ;
	  Assert.assertEquals(comments, positionModel.getComments(placeName));
  }

	//@Test(enabled = false)
	  @Test(dataProvider = "test3")
  public void getMyRating(String email, String placeName) {
		  Assert.assertEquals(-1.0, positionModel.getMyRating(email, placeName));
  }

	@Test(enabled = false)
	//  @Test(dataProvider = "test4")
  public void getNearestLocation(Double lat, Double lon) {
		  PositionModel position = new PositionModel() ;
		  Assert.assertEquals(position, positionModel.getNearestLocation(lat, lon));
  }

	@Test(enabled = false)
	//  @Test(dataProvider = "test2")
  public void getPlace(String placeName) {
		  PositionModel position = new PositionModel() ;
		  Assert.assertEquals(position, positionModel.getPlace(placeName));
  }

	@Test(enabled = false)
	//  @Test(dataProvider = "test2")
  public void placeFound(String placeName) {
		  PositionModel position = new PositionModel() ;
		  Assert.assertEquals(position, positionModel.placeFound(placeName));
  }

	@Test(enabled = false)
	//  @Test(dataProvider = "test5")
  public void ratePlace(String email, String placeName, double rate) {
		  Assert.assertEquals(true, positionModel.ratePlace(email, placeName, rate));
  }
}
