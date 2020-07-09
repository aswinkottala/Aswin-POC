package StepDefinitions;

import Utilities.Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.testng.Assert;

import static org.testng.Assert.assertTrue;

public class SearchResultsSteps extends Util {

    HomePage homePage;
    SearchPage searchPage;
    String notfoundvehicle="";
    String verificationErrors;

    public static class vehcileDetails {
        private String REGISTRATION;
        private String MAKE;
        private String MODEL;
        private String COLOR;
        private String YEAR;
    }

    @Given("I can navigate to cartaxcheck homepage")
    public void i_can_navigate_to_cartaxcheck_homepage() {
        initializeTest();
        homePage = new HomePage();
        homePage.OpenHomePage();
    }

    @When("I look for the vehicle registrations from the input file and search on the cartaxcheck website")
    public void I_look_for_the_vehicle_registrations_from_the_input_file_and_search_on_the_cartaxcheck_website() throws FileNotFoundException {
        searchPage = new SearchPage();
        List<String> registrations=homePage.GetVehicleRegistrations();
        vehcileDetails vehicle = new vehcileDetails();

        for (int i=0;i<registrations.size(); i++)
        {
            homePage.VehicleSearch(registrations.get(i));
            boolean found=searchPage.vechilefound(registrations.get(i));

            if (searchPage.vechilefound(registrations.get(i))==false)
            {
                List<String> actualvehicledetails=searchPage.GetvehicleDetails();
                ExpectedVehicleDetails(actualvehicledetails);

            }
            else
            {
                notfoundvehicle=notfoundvehicle+" "+registrations.get(i);
                captureScreenshot();

            }
            searchPage.BackToHomepage();
        }
    }

    @Then("check for expected vehicle details")
    public void check_for_expected_vehicle_details() {

        //Assert.assertFalse(condition)
        //Assert.fail("you wandered onto the wrong path");
        exitTest();
        if (notfoundvehicle!=null)
        {
            System.out.println("Vehicle Not Matched from the output file : " + notfoundvehicle);
            Assert.fail("Element Not Found !" + notfoundvehicle);
        }

    }
    public void ExpectedVehicleDetails(List<String> actualvehicledetails) throws FileNotFoundException {
        File outputfile = new File("C:\\car_output.txt");
        //initialize the scanner
        Scanner scanoutputfile = new Scanner(outputfile);
        //iterate through the file line by line
        List<String> results = new ArrayList<String>();
        List<vehcileDetails> vehcileDetails=  new ArrayList<>();
        scanoutputfile.nextLine();
        while(scanoutputfile.hasNextLine()){
            // scan for names on the content of the file
            String str = scanoutputfile.nextLine();
            List<String> vehicle_details_array = Arrays.asList(str.split(","));

            if (actualvehicledetails.get(0).equals(vehicle_details_array.get(0))) {
                if (actualvehicledetails.equals(vehicle_details_array))
                {
                    System.out.println("Vehicle Matched from the output file :"+actualvehicledetails.get(0));
                }
            }
        }
        // close the scanner object;
        scanoutputfile.close();
        //return vehicleregarray;
    }

}
