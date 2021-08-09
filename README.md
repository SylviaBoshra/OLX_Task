# OLX Task
> Test automation assignment for www.airbnb.com website
> 

## Table of Contents
* [General Info](#info)
* [Libraries and Frameworks](#lib)
* [Programming Language](#lang)
* [Code Details](#code)
* [Contact](#contact)

<a name="info"></a>
## General Information
- TWeb Test automation project using Java, Selenium, and TestNG
- Website URL: www.airbnb.com
- In the project, we are doing the following
  -  Verify that the results match the search criteria
  -  Verify that the results and details page match the extra filters
  -  Verify that a property is displayed on the map correctly

<a name="lib"></a>
## Libraries and Frameworks
- Selenium - Web automation
- TestNG - Test execution and reporting

<a name="lang"></a>
## Programming Language
- Java

<a name="code"></a>
## Code Details
- In the code, We have 2 classes
    - **Myclass.java** : It is the main class,Responsible for go to website, Insert search data, Verify data, test results, and property details are correct <br />
    - **TestDate.java** : It is responsible for geting current date , calculate the check in and check out dates, Choose the correct dates from the date picker <br />
  
### Myclass.java Functions
Function Name | Description   |
|------------- |-------------- |
|OpenWebsite  | Open Google chrome and go to the website URL and verify that it is opened successfully  |
|verifysearchdata  | Insert search data (Location - Check in Date - Check out Date - Guests) and click on the search button and in the next page we Verify that the applied filters are correct (in the filters area and search results header).  |
| Myclass.java  | verifysearchresult  | Verify that the properties displayed on the first page can accommodate at least the selected number of guests.|
| verifymoredetails  | Add more filters (Number of bedrooms and pool access) and Verify that the results and details page match the extra filters |
| verifymap  | Verifying that the property is displayed on the map and the color of the pin changes (upon hover) and Verify that the details shown in the map popup are the same as the ones shown in the search results.  |
| Closedriver  | Close the browser  |

<br/>
   
### TestDate.java Functions
| Function Name | Description   |
| ------------- | ------------- |
| getcurrentdate  | Get the current Date  |
| get_date_after_days  |  get the date after adding some days (It is used to calculate the check in and check out date |
| convert_date_to_str  |  Covert the date to string |
| getdayfromdate  |  Extract the value of day (from 1 to 31) from a date |
| getmonthyear  | Extract the month and year from the date  |
|  clickGivenDay | Click on the required date from the date picker  |
| DatePicker  | Looping on the date picker to search for the day that the customer will check in and check out  |


<a name="contact"></a>
## Contact
Created by Sylvia Boshra <br />
**Phone number**: 01200973414 <br />
**Email**: Sylviaboshra@hotmail.com

