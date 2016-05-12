package com.Outdoor.models;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CommentModelTest {

	CommentModel commentModel = new CommentModel() ;
	@DataProvider(name = "test1")
	  public Object[][] dataProvider(){
		  return new Object[][] {{"amazing!", "test94@gmail.com", 50}} ;
	  }
	
	@DataProvider(name = "test2")
	  public Object[][] dataProvider2(){
		  return new Object[][] {{"amazing!", "test94@gmail.com", "Giza"}} ;
	  }
	
	//@Test(enabled = false)
	  @Test(dataProvider = "test1")
  public void addCommentToBrand(String text, String email, int brandID) {
		  Assert.assertEquals(true, commentModel.addCommentToBrand(text, email, brandID));
  }

	  //@Test(enabled = false)
	  @Test(dataProvider = "test1")
  public void addCommentToCheckin(String text, String email, int checkinID) {
		  Assert.assertEquals(true, commentModel.addCommentToBrand(text, email, checkinID));
  }
	  
	@Test(enabled = false)
	//  @Test(dataProvider = "test2")
  public void addCommentToPlace(String text, String email, String placeName) {
		  Assert.assertEquals(true, commentModel.addCommentToPlace(text, email, placeName));
  }
}
