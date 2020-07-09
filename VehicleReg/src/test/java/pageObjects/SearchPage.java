package pageObjects;

import Utilities.Util;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends Util {

    @FindBy(how = How.XPATH, using = "//div[@id='head']//div[contains(@class,'menuContainer')]/span")
    private WebElement menuContainer_link;

    @FindBy(how = How.XPATH, using = "//nav[@id='menu']//a[text()='Free Car Check']")
    private WebElement Free_Car_Check_Link;

    @FindBy(how = How.XPATH, using = ".//dt[text()='Registration']/..//dd")
    private WebElement Registration;

        @FindBy(how = How.XPATH, using = "//dt[text()='Make']/..//dd")
    private WebElement Make;

    @FindBy(how = How.XPATH, using = "//dt[text()='Model']/..//dd")
    private WebElement Model;

    @FindBy(how = How.XPATH, using = "//dt[text()='Colour']/..//dd")
    private WebElement Colour;

    @FindBy(how = How.XPATH, using = "//dt[text()='Year']/..//dd")
    private WebElement Year;

    @FindBy(how = How.XPATH, using = "//a[text()='Try Again']")
    public WebElement vechilenotfound;




    public void OpenHomePage(){
        driver.navigate().to("https://cartaxcheck.co.uk/");
    }

    public void BackToHomepage(){
        click(menuContainer_link);
        click(Free_Car_Check_Link);
    }

    public boolean vechilefound(String reg){

        //AssertElementText(Registration,reg);

        return isElementPresent(vechilenotfound);

    }





    public List<String> GetvehicleDetails()
    {
        List<String> vehicledetails = new ArrayList<String>();


        vehicledetails.add(getText(Registration));
        vehicledetails.add(getText(Make));
        vehicledetails.add(getText(Model));
        vehicledetails.add(getText(Colour));
        vehicledetails.add(getText(Year));
        return vehicledetails;
    }

    public void ReadInputFile() {

        //Check link to other languages is displayed
        //isElementPresent(lang);


    }


    public SearchPage()
    {
        PageFactory.initElements(driver,this);
    }
}

