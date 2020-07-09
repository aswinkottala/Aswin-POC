# Aswin-POC

Please run the feature file to run the test and this has been executed against chromedriver ver 83 and chrome version 83

Please see below for the test results after the execution
Vehicle Matched from the output file :DN09HRM
***Placed screen shot in C:\Solution\vehiclereg\target\screenshots\screenshot_20200709_092557.png ***
Vehicle Matched from the output file :KT17DLX
Vehicle Matched from the output file :SG18HTN
Waiting for 2 Seconds before Capturing the Screenshot
Vehicle Not Matched from the output file :  BW57 BOW

java.lang.AssertionError: Element Not Found ! BW57 BOW
	at org.testng.Assert.fail(Assert.java:96)
	at StepDefinitions.SearchResultsSteps.check_for_expected_vehicle_details(SearchResultsSteps.java:77)
	at ✽.check for expected vehicle details(file:/C:/Solution/VehicleReg/src/test/resources/SearchResults.feature:6)


Failed scenarios:
/C:/Solution/VehicleReg/src/test/resources/SearchResults.feature:3 # Validate Search Results and compare with the output file

1 Scenarios (1 failed)
3 Steps (1 failed, 2 passed)
