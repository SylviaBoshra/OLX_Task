package newpackage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

@Test
public class Myclass {
	
	public String driverPath = "Drivers\\chromedriver.exe";  //chrome driver
    public String baseUrl = "https://www.airbnb.com/";  //airbnb website
    public String location = "Rome, Italy";  //Location we search for
    public Integer checkin = 7;  //Check-In date (7 days) after the current date
    public Integer checkout = 7; //Check-Out date (7 days) after the Check-In date
    public Integer Adults_Count = 2;  //Count of Adults
    public Integer Children_Count = 1; //Count of children
    public Integer Infants_Count = 0;  //count of Infants
    public Integer Bedrooms = 5; //count of bedrooms
    public WebDriver driver ; 
    public TestDate Mydate;
    
    
    
     
  @BeforeTest
  public void OpenWebsite() {
       
      System.setProperty("webdriver.chrome.driver", driverPath);
      driver = new ChromeDriver();
      driver.get(baseUrl);
      driver.manage().window().maximize();
      String expectedTitle = "Airbnb: Vacation Rentals, Cabins, Beach Houses, Unique Homes & Experiences";
      String actualTitle = driver.getTitle();
      Assert.assertEquals(actualTitle, expectedTitle);

      Reporter.log("Airbnb Website is opened successfully");
  }
  
  @Test (priority = 1)
  public void verifysearchdata() throws InterruptedException
  {
	  Mydate = new TestDate();
	  
	  //Wait for 2 seconds
	  Thread.sleep(2000);
	  
	  //Find location input field
	  WebElement location_element = driver.findElement(By.xpath("//*[@id=\"bigsearch-query-detached-query-input\"]"));  
	  location_element.click();
	  
	  //Insert the required location
	  location_element.sendKeys(location);

	  
	  //Get current date
	  LocalDate current = Mydate.getcurrentdate();
	  
	  //Calculate the check in date
	  LocalDate checkin_date = Mydate.get_date_after_days(current, checkin);
	  
	  //Click on check in field
	  WebElement startdate = driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div/div[1]/div[1]/div/header/div/div[2]/div[2]/div/div/div/form/div[2]/div/div[3]/div[1]/div/div/div[2]"));
	  startdate.click();
	  Thread.sleep(500);
	  
	  //Check the check in date from the date picker
	  Mydate.DatePicker(driver, checkin_date);
	  
	  //Calculate the check out date
	  LocalDate checkout_date = Mydate.get_date_after_days(checkin_date, checkout);	  

	  //Check the check out date from the date picker
	  Mydate.DatePicker(driver, checkout_date);	  

	  
	  //Click on add guests field
	  driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div/div[1]/div[1]/div/header/div/div[2]/div[2]/div/div/div/form/div[2]/div/div[5]/div[1]/div/div[2]")).click();
	  
	  //Add adults
	  for(int i=0;i<Adults_Count;i++)
	  {
		  driver.findElement(By.xpath("//*[@id=\"stepper-adults\"]/button[2]")).click();
		  Thread.sleep(200);
	  }
	  
	  //Add children
	  for(int i=0;i<Children_Count;i++)
	  {
		  driver.findElement(By.xpath("//*[@id=\"stepper-children\"]/button[2]")).click();
		  Thread.sleep(200);
	  }
	  
	  //Add Infants
	  for(int i=0;i<Infants_Count;i++)
	  {
		  driver.findElement(By.xpath("//*[@id=\"stepper-infants\"]/button[2]")).click();
		  Thread.sleep(200);
	  }

	  
	  //Click on search button
	  driver.findElement(By.className("_1mzhry13")).click();

	  
	  //Wait for 5 seconds to make sure that the result page is loaded
	  Thread.sleep(5000);
	  
	  //Verify Location
	  String actuallocation = driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div/div[1]/div[1]/div/header/div/div[2]/div[1]/div/button[1]/div")).getText();
      Assert.assertTrue(location.contains(actuallocation));       

	  Reporter.log("Location is verified in the filters area");
      
      //verify Date
      String ExpectedDate = "";
      
      String month_shortname_in = checkin_date.getMonth().toString().substring(0,3);;
      String month_shortname_out = checkout_date.getMonth().toString().substring(0,3);
      Integer year_in = checkin_date.getYear();
      Integer year_out = checkout_date.getYear();
      
      String checkin_str = Mydate.convert_date_to_str(checkin_date);	  
	  Integer int_day_checkin = Mydate.getdayfromdate(checkin_str);
      
	  String checkout_str = Mydate.convert_date_to_str(checkout_date);	  
	  Integer int_day_checkout = Mydate.getdayfromdate(checkout_str);
      
      //Get the check in day
      String checkin_day = int_day_checkin.toString();
      
      //Get the check out day
      String checkout_day = int_day_checkout.toString();
      
      //Get the actual date from the screen
      String ActualDate = driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div/div[1]/div[1]/div/header/div/div[2]/div[1]/div/button[2]/div")).getText();
      
      //Check if the check in and check out dates are in the same year
      if(year_in.toString().equals(year_out.toString()))
      {
    	  //Check if the check in and check out dates are in the same month
    	  if(month_shortname_in.equals(month_shortname_out))
    	  {
    		  
    		  ExpectedDate = month_shortname_in + " " + checkin_day + " – " + checkout_day;
    	  }
    	  //Same year but not same month
    	  else
    	  {
    		  ExpectedDate = month_shortname_in + " " + checkin_day + " – " + month_shortname_out + " "   +checkout_day;  		  
    	  }
    	
      }
      else
      {
    	  ExpectedDate = month_shortname_in + " "+ checkin_day +", " + year_in.toString() + " – " + month_shortname_out + " "+ checkout_day +", " + year_out.toString();
      }
     
      Assert.assertEquals(ActualDate.toLowerCase(),ExpectedDate.toLowerCase());
      
      Reporter.log("Checkin and Checkout dates are verified in the filters area");
      
      //Verify number of Guests  //Infants are not calculated
      Integer guests_Count = Adults_Count + Children_Count;
      
      //Check the actual guests count from the screen
      String ActualGuestCount_filterarea = driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div/div[1]/div[1]/div/header/div/div[2]/div[1]/div/button[3]/div[1]")).getText();
      String ActualGuestCount_Numbers = ActualGuestCount_filterarea.replaceAll("[^0-9]", "");
      if(guests_Count > 0)
      {
    	  Assert.assertEquals(guests_Count.toString(),ActualGuestCount_Numbers);
      }
      Reporter.log("Guests count are verified in the filters area");
      
      //Verify Header
      String Actualheader = driver.findElement(By.xpath("//*[@id=\"ExploreLayoutController\"]/div[1]/div[1]/div[1]/div/div[1]/section/div[1]")).getText();
      String DateHeader = ActualDate.replaceAll("–", "-");
      
      Assert.assertTrue(Actualheader.contains(ActualGuestCount_filterarea));
      Assert.assertTrue(Actualheader.contains(DateHeader));
      
      Reporter.log("Data in the header are verified successfully");
  }
  
  
  @Test (priority = 2)
  public void verifysearchresult() throws InterruptedException
  {
	  
	  //Search in all properties for number of guests that can accommodate
	  String Text = "";
	  boolean correct = true;
	  List<WebElement> Guests_list = driver.findElements(By.className("_12oal24"));
	  for (WebElement element : Guests_list)
	  {
		  Text = element.findElement(By.className("_3c0zz1")).getText();
		  String P_Guests_str = Text.substring(0,2).replaceAll("[^0-9]", "");
		  Integer P_Guests =  Integer.parseInt(P_Guests_str);
		  Integer Actual_Guests_Count = Adults_Count + Children_Count;
		  
		  //Confirm that the property accommodate number of guests equal to or more than the number of guests we search for
		  Assert.assertTrue(P_Guests >= Actual_Guests_Count);
		  
		  if(P_Guests < Actual_Guests_Count)
		  {
			  correct = false;
		  }
	  }
	  if(correct)
		  Reporter.log("the properties displayed on the first page can accommodate at least the selected number of guests.");
	  
  }
  
  @Test (priority = 3)
  public void verifymoredetails() throws InterruptedException{
       
	  //Click on "More Filters" button
	  WebElement MoreFilters_Button = driver.findElement(By.xpath("//*[@id=\"menuItemButton-dynamicMoreFilters\"]/button")); 
	  MoreFilters_Button.click();
	  Thread.sleep(2000);
	  
	  //Add bedrooms
	  for(int i=0;i<Bedrooms;i++)
	  {
		  driver.findElement(By.xpath("//*[@id=\"filterItem-rooms_and_beds-stepper-min_bedrooms-0\"]/button[2]")).click();
		  Thread.sleep(200);
	  }
	  
	  //scroll down until pool checkbox is displayed
	  WebElement element = driver.findElement(By.xpath("//*[@id=\"filterItem-facilities-checkbox-amenities-7-row-checkbox\"]"));
	  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	  Thread.sleep(500); 
	  
	  //Select pool check box
	  driver.findElement(By.xpath("//*[@id=\"filterItem-facilities-checkbox-amenities-7-row-checkbox\"]")).click();
	  Thread.sleep(500);
	  //Click on show stays to submit the more filters
	  driver.findElement(By.xpath("/html/body/div[12]/section/div/div/div[2]/div/footer/button")).click();
	  
	  //Wait for 2 seconds to make sure that the page is updated successfully
	  Thread.sleep(2000); 
	  
	  //Search in all properties for number of bedrooms
	  String Text = "";
	  boolean correct = true;
	  List<WebElement> data_list = driver.findElements(By.className("_12oal24"));
	  for (WebElement xelement : data_list)
	  {
		  //Get the full text
		  Text = xelement.findElement(By.className("_3c0zz1")).getText();
		  //remove the part of guests count
		  String Property_bedrooms = Text.substring(Text.indexOf("guests")+9,Text.indexOf("bedroom")-1);
		  Integer P_bedrooms =  Integer.parseInt(Property_bedrooms);
		  //Check the properties bedrooms greater than or equal to the required
		  Assert.assertTrue(P_bedrooms >= Bedrooms);
		  if(P_bedrooms < Bedrooms)
			  correct = false;
	  }
	  
	  if(correct)
		  Reporter.log("he properties displayed on the first page have at least the number of selected bedrooms.");
	  
	  //Open first property
	  driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div/div[1]/main/div/div[1]/div[1]/div[2]/div/div[2]/div/div/div[3]/div/div/div/div/div[1]/div/div/div[2]/div/div/div/div[2]/div[1]/div/div[1]/div[1]/div/span/div/a")).click();
	  
	  //Go to second tab to view property amenities
	  ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	  driver.switchTo().window(tabs.get(1));
	  
	  //Wait for 5 seconds to make sure that the page is loaded
	  Thread.sleep(5000); 
	  
	  //scroll down until amenities button are displayed
	  WebElement amenities = driver.findElement(By.xpath("//*[@id=\"site-content\"]/div/div[1]/div[3]/div/div[1]/div/div/div[5]/div/div[2]/section/div[2]/div/h2"));
	  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", amenities);
	  Thread.sleep(500);
	  
	  //Click on amenities button
	  driver.findElement(By.xpath("//*[@id=\"site-content\"]/div/div[1]/div[3]/div/div[1]/div/div/div[5]/div/div[2]/section/div[4]/a")).click();
	  
	  //Wait until pop up menu is opened
	  Thread.sleep(2000);
	  
	  //scroll down until Parking and facilities are displayed
	  WebElement Facilities = driver.findElement(By.xpath("/html/body/div[12]/section/div/div/div[2]/div/div[3]/div/div/div/section/div[2]/div[11]/div[1]/h3"));
	  Assert.assertEquals(Facilities.getText(), "Parking and facilities");
	  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Facilities);
	  Thread.sleep(500); 
	  
	  String Facilities_text = (driver.findElement(By.xpath("/html/body/div[12]/section/div/div/div[2]/div/div[3]/div/div/div/section/div[2]/div[11]")).getText());
	  
	  Thread.sleep(500);
	  
	  //Check Facilities contains Pool
	  Assert.assertTrue(Facilities_text.contains("Pool"));
	  Reporter.log("The first property has a pool");
	  
  }
  

  @Test (priority = 4)
  public void verifymap() throws InterruptedException
  {
	  //Go to first tab to view properties list
	  ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	  driver.switchTo().window(tabs.get(0));
	  
	  //Read First property details
	  String prop_location = driver.findElements(By.className("_1olmjjs6")).get(0).getText().replace(" in ", " ");
	  String prop_details = driver.findElement(By.xpath("//*[@id=\"ExploreLayoutController\"]/div[1]/div[1]/div[2]/div/div[2]/div/div/div[3]/div/div/div/div/div[1]/div/div/div[2]/div/meta[1]")).getAttribute("content");
	  String prop_reviews = driver.findElements(By.className("_18khxk1")).get(0).getText();
	  String prop_price = driver.findElements(By.className("_1gi6jw3f")).get(0).getText();
	  
	  
	  //Hover on the first property
	  WebElement First_prop = driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div/div[1]/main/div/div[1]/div[1]/div[2]/div/div[2]/div/div/div[3]/div/div/div/div/div[1]/div/div/div[2]/div/div/div/div[2]/div[1]/div/div[1]/div[1]/div/span/div/a"));
	  Actions action = new Actions(driver);
	  action.moveToElement(First_prop).perform();
	  Thread.sleep(1000);
	  
	  //Verify that the property is displayed on the map and the color of the pin changes 
	  Boolean property_found_on_map = false;
	  List<WebElement> map_elements = driver.findElements(By.className("_fwxpgr"));
	  for (WebElement element : map_elements) {
		  Thread.sleep(500);
		  WebElement element_style = element.findElement(By.xpath("./div/div"));
		  String color = element_style.getCssValue("color");
		  String background_color = element_style.getCssValue("background-color");
		  
		  if((color.equals("rgba(255, 255, 255, 1)")) && background_color.equals("rgba(34, 34, 34, 1)"))
		  {
			  property_found_on_map = true;
			  //Click on it
			  element.click();
			  break;
		  }
		  
	  }
	  Assert.assertTrue(property_found_on_map);
	  Reporter.log("the property is displayed on the map and the color of the pin changes (upon hover)");
	  
	  
	  Thread.sleep(500);
	  
	  
	  //Confirm that the pop up is displayed
	  Assert.assertTrue(driver.findElement(By.className("_1j2gyhf")).isDisplayed());
	  Reporter.log("A pop up is displayed with the property data");
	  
	  //Get the data of the property which is displayed on the map
	  WebElement popup_menu = driver.findElement(By.className("_1j2gyhf"));
	  String map_location =  popup_menu.findElement(By.className("_194e2vt2")).getText().replace(" · ", " ");
	  String map_details =  popup_menu.findElement(By.className("_1spi1ps9")).getText();
	  String map_reviews =  popup_menu.findElement(By.className("_18khxk1")).getText().replace(")", " reviews)");
	  String map_price =  popup_menu.findElement(By.className("_1klfbd5m")).getText();


	  //Verify that the details shown in the map popup are the same as the ones shown in the search results
	  Assert.assertEquals(prop_location, map_location);
	  Assert.assertEquals(prop_details, map_details);
	  Assert.assertEquals(prop_reviews, map_reviews);
	  Assert.assertEquals(prop_price, map_price);
	  
	  Reporter.log("the details shown in the map popup are the same as the ones shown in the search results");
	  
  }
  
  
  @AfterTest
  public void Closedriver()
  {
	  driver.quit();
  }
}