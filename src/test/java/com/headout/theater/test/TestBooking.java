package com.headout.theater.test;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.headout.theater.obj.Base;
import com.headout.theater.obj.BrowserLaunch;
import com.utility.ExcelReader;

public class TestBooking extends BrowserLaunch{

	Logger log = Logger.getLogger(TestBooking.class);
	//This will read the data from excel and provide to test	
	@DataProvider(name="booking_details")
	public Object[][] userdetails() throws Exception{
		log.info("***************** Reading the test data from excel *****************");
		Object[][] values=ExcelReader.getTableArray(".//data/london_booking.xlsx", "booking");
		return values;
	}
	//This test will book the ticket 
	@Test(dataProvider="booking_details")
	public void bookShow(String app_title,String show_name,String date,String category,String row,String seat_number,String first_name,
			String last_name,String email,String confirm_email,String phone_number,String card_number,String expiry_date,String ccv,
			String card_holder_name) throws InterruptedException{
		log.info("*********************Starting Test-bookShow****************************");
		Base b=new Base(driver);
		assertTrue(b.isAppLaunch(app_title),"App does not launched");
		log.info("*********************App Launched Sucessfully****************************");
		b.wait_for_element_and_perform_action("css", "input[type='text']", "input", show_name);
		log.info("********************Searching for the show****************************");
		b.wait_for_element_and_perform_action("xpath", "//div[@class='res-title hidden']//following-sibling::*", "click", "");
		b.waitForBookNowButton("//div[contains(text(),'Book Now')]");
		assertTrue(b.verify_element_is_displaying("xpath", "//div[contains(text(),'Book Now')]"), "Book Now Button is not displaying");
		log.info("*********************Clicking on book buuton****************************");
		b.wait_for_element_and_perform_action("xpath", "//div[contains(text(),'Book Now')]", "click", "");
		log.info("*********************Switching the window ****************************");
		b.switchWindow();
		log.info("*********************Selecting the date*******************************");
		b.pickUpDate("//div[@class='date-big-wrapper   ']//span[@class=' notranslate']",date);
		log.info("*********************Selecting the Seat*******************************");
		b.selectTheSeat(category,row,seat_number);
		assertTrue(b.verify_element_is_displaying("xpath", "//div[@class='next-button selectable']"), "Next Button is not displaying");
		b.wait_for_element_and_perform_action("xpath", "//div[@class='next-button selectable']", "click", "");
		log.info("********************Entering the user details******************************");
		b.wait_for_element_and_perform_action("name", "First Name", "input", first_name);
		b.wait_for_element_and_perform_action("name", "Last Name", "input", last_name);
		b.wait_for_element_and_perform_action("xpath", "//input[@name='Email']", "input", email);
		b.wait_for_element_and_perform_action("xpath", "//input[@name='Confirm Email']", "input", confirm_email);
		b.wait_for_element_and_perform_action("xpath", "//input[@name='Phone']", "input", phone_number);
		log.info("********************Entering the card details******************************");
		b.wait_for_element_and_perform_action("id", "card-number", "input", card_number);
		b.wait_for_element_and_perform_action("name", "Expiry (MM/YY)", "click", "");
		b.wait_for_element_and_perform_action("name", "Expiry (MM/YY)", "input", expiry_date);
		b.wait_for_element_and_perform_action("xpath", "//input[@id='card-cvv']", "input", ccv);
		b.wait_for_element_and_perform_action("xpath", "//input[@id='card-name']", "input", card_holder_name);
		assertTrue(b.verify_element_is_displaying("xpath", "//div[@class='product-checkout-button hero-button']"), 
				"Complete My Booking Button is not displaying");
		log.info("********************clicking on complete booking******************************");
		b.wait_for_element_and_perform_action("xpath", "//div[@class='product-checkout-button hero-button']", "click", "");
		log.info("********************Ending the Test-bookShow********************************");		
	}
}
