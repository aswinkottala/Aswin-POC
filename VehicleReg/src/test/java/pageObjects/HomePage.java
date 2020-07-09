package pageObjects;

import Utilities.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomePage extends Util {

    @FindBy(how = How.XPATH, using = ".//input[@id='vrm-input']")
    private WebElement Vehicle_Reg;

    @FindBy(how = How.XPATH, using = ".//button[text()='Free Car Check']")
    private WebElement Free_Car_Check;

    public void OpenHomePage(){
        driver.navigate().to("https://cartaxcheck.co.uk/");
    }

    public void VehicleSearch(String vehiclereg) {
            WaitForElement(Vehicle_Reg);
            //Enter a search string
            sendKeys(Vehicle_Reg,vehiclereg);

            //Click on the Free Car Check Button
            click(Free_Car_Check);




    }





    public HomePage()
    {
        PageFactory.initElements(driver,this);
    }
}

