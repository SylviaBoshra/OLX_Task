package newpackage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class TestDate {
	
	// Constructor
	public TestDate() {}
	
	//Get the current Date
    public LocalDate getcurrentdate () 
    {
      LocalDate currentDate = LocalDate.now();
      return currentDate;
    }
    
   //Add days to current day to calculate the check in and check out date
   public LocalDate get_date_after_days (LocalDate mydate, Integer x)
   {
	   //Adding x days to the current date
	   LocalDate result = mydate.plus(x, ChronoUnit.DAYS);
	   return result;
   }
   
   //Convert date to string so I can get the value for day,month, and year
   public String convert_date_to_str (LocalDate mydate)
   {
	   DateTimeFormatter  DateFor = DateTimeFormatter.ofPattern("dd LLLL yyyy");
	   String stringDate = mydate.format(DateFor);
	   return stringDate;
   }
   
   //Extract the value of day from date
   public Integer getdayfromdate (String datestring)
   {
	   String str = datestring.substring(0,2);
	   Integer day = Integer.parseInt(str);
	   return day;
   }
   
   //Extract the value of month from date
   public String getmonthyear (String datestring)
   {
	   String monthyear = datestring.substring(3);
	   return monthyear;
   }
   
   //Click the exact day on the date picker
   public void clickGivenDay(List<WebElement> elementList, String day) 
   {
	   
       elementList.stream()
           .filter(element -> element.getText().contains(day))
           .findFirst()
           .ifPresent(WebElement::click);   
   }
   
   
   
   
   public void DatePicker(WebDriver driver, LocalDate check_date) throws InterruptedException
   {	  
	   String check_str = convert_date_to_str(check_date);	  
	   Integer int_day_checkin = getdayfromdate(check_str);
	   String check_day = int_day_checkin.toString();
	   String check_monthyear = getmonthyear(check_str);
	   
	   
	   
	   //Search for day month year for the check in date
	   boolean Checkin_Found = false;
	   while (!Checkin_Found)  
	   {
		   Thread.sleep(500);
			String MonthYear = driver.findElement(By.xpath("//*[@id=\"panel--tabs--0\"]/div[1]/div/div/div/div[2]/div[2]/div/div[2]/div/div/h2")).getText();
			  
			if(MonthYear.equals(check_monthyear))
			{
				WebDriverWait wait = new WebDriverWait(driver, 300/*timeOutInSeconds*/);
				WebElement dateWidgetFrom = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("_14676s3"))).get(0);
				List<WebElement> columns = dateWidgetFrom.findElements(By.tagName("td"));
				clickGivenDay(columns, check_day);
				Checkin_Found = true;
			}
			else
			{
				driver.findElement(By.xpath("//*[@id=\"panel--tabs--0\"]/div[1]/div/div/div/div[2]/div[1]/div[2]/div/button")).click();
			}
		  }
		  
		  Thread.sleep(500);
   }
   
}
