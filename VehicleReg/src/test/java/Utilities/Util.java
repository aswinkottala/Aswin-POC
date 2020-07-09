package Utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class Util {
    protected static WebDriver driver = null;
    private static String parentWindowHandler;
    private static Set<String> oldWindows;
    protected static int defaultTimeout = 2;


    protected static void sleep(int waitValue) {
        //System.out.println("Sleeping for '" + waitValue + "' half seconds");
        try {
            Thread.sleep(waitValue * 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("SameParameterValue")
    protected static void navigate(String URL) {
        System.out.println("Navigating to URL: " + URL);
        driver.get(URL);
    }


    protected static boolean isElementPresent(WebElement element) {
        //System.out.println("Checking to see if Element is Present :" + element.toString());
        try {
            WaitForElement(element);
            element.isDisplayed();

            //System.out.println("Checking to see if Element is Present : Element Found !");
            return true;
        } catch (Exception e) {
            //System.out.println("Checking to see if Element is Present : Element Not Found !");
            return false;
        }
    }


    protected static void WaitForElement(WebElement element) {
        Boolean found=false;
        try {
            for (int i = 0; i < defaultTimeout ; i++) {
                try {
                    if(i==0)
                       // System.out.println("Waiting for Element : Element : " + element.toString());
                    found=element.isDisplayed();
                    if(found){
                        found = true;
                        break;
                    }
                    else
                        sleep(1);
                } catch (Exception e) {
                    sleep(1);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while Waiting for Element, Exception :" + e.getMessage());


        }

        //Assert.assertTrue(found, "WaitForElement : Element Found ");
    }

    protected static void WaitForElementToBeClickable(WebElement element) {
        Boolean waiting=true;
        int timeout = defaultTimeout;
        try {
            WaitForElement(element);
            //System.out.println("Waiting for Element To Be Clickable :" + element.toString());
            for (int i = timeout; i > 0; i--) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    wait.until(ExpectedConditions.elementToBeClickable(element));
                    break;
                } catch (Exception e) {
                    sleep(1);
                }
            }
        }catch (Exception e){
            WaitForPageRefresh();
        }
    }


    protected static WebElement ElementByXPath(String xpath) {

//        WaitForElement(driver.findElement(By.xpath(xpath)));
//        return driver.findElement(By.xpath(xpath));

        WebElement element = null;

        Boolean found=false;
        System.out.println("Waiting for Element By XPath:" + xpath + ", a maximum of "+defaultTimeout+" seconds");

        try {
            for (int i = 0; i < defaultTimeout ; i++) {
                try {
                    element = driver.findElement(By.xpath(xpath));
                    found=element.isDisplayed();
//                    found = driver.findElement(By.xpath(xpath)).isDisplayed();
                    if(found){
                        found = true;
                        break;
                    }
                    else
                        sleep(1);
                } catch (Exception e) {
                    sleep(1);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while Waiting for Element, Exception :" + e.getMessage());
        }

        return element;
    }

    protected static void ClickElementByXPath(String xpath) {
        boolean found = false;
       // System.out.println("Clicking on Element with Xpath:" + xpath);
        for (int i = defaultTimeout; i > 0; i--) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 1);
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(xpath))));
                driver.findElement(By.xpath(xpath)).click();
                found = true;
                break;
            } catch (Exception e) {
                sleep(1);
                //Log("Sleeping for 1 Second");
            }
        }
        Assert.assertTrue(found==true, "ClickElementByXPath : Element NOT Found");
    }

    protected static String getValue(WebElement element) {
        String value="";
            try {
                WaitForElement(element);
                value = element.getAttribute("value");
            } catch (NoSuchElementException e) {
                sleep(5);
                value = element.getAttribute("value");
            }
        return value;
    }

    protected static String getText(WebElement element) {
        String value;
        WebDriverWait wait = new WebDriverWait(driver, defaultTimeout);
        wait.until(ExpectedConditions.visibilityOf(element));

        if(element.getTagName().equals("select")) {
            value = getValue(element);
            value = element.findElement(By.xpath(".//option[@value='" + value + "']")).getAttribute("text");
            System.out.println("element is a select: value: = "+value);
        }
        else if (element.getTagName().equals("td")) {
            value = element.getAttribute("textContent");
            //System.out.println("element is a td: value: = "+value);
        }
        else {
            value = element.getAttribute("text");
            if(value==null)
                value = element.getText();
            //System.out.println("element is not a select: value: = " + value);
        }

        return value;
    }

    protected static void selectValue(WebElement element, String optionValue) {
        System.out.println("Select Value - " + element.toString() + " : '" + optionValue + "'");
        WaitForElement(element);
        WaitForElementToBeClickable(element);
        try {
            new Select(element).selectByVisibleText(optionValue);
            return;
        } catch (NoSuchElementException e) {
          Log("selectValue: Unable to select option by Visible Text");
        } catch (StaleElementReferenceException e) {
            new Select(element).selectByVisibleText(optionValue);
            return;
        }

        try {
            new Select(element).selectByValue(optionValue);
            return;
        } catch (NoSuchElementException e) {
            Log("selectValue: Unable to select option by Value");
        }

        try {
            List<WebElement> options = element.findElements(By.tagName("option"));
            for (WebElement option : options) {
                WaitForElementToBeClickable(option);
                click(element);
                if (option.getAttribute("text").contains(optionValue))
                    click(option);
                else
                    Log("selectValue: Unable to select option using Text Attribute");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred in 'selectValue' method :" + e.getMessage());
        }
    }

    protected static void setValue(WebElement element, String textValue) {
        try {
            System.out.println("Set Value - " + element.toString() + " : '"+ textValue + "'");

            element.sendKeys(textValue);
        } catch (Exception e) {
            System.out.println("Exception occurred in 'setValue' method :" + e.getMessage());
        }
    }


    protected static void AssertDisplayed(WebElement element)
    {
        Log("Verifying Element is Displayed :" + element.toString());
        WaitForElement(element);
        element.isDisplayed();
    }

    protected static void AssertNotDisplayed(WebElement element)
    {
        boolean found;
        if(element!=null)
        {
            System.out.println("Verifying Element is Not Displayed :" + element.toString());
            try {
                found = element.isDisplayed();
            }catch(NoSuchElementException e) {
                found = false;
            }
        }
        else
            found= false;
        if(found==true) {
            System.out.println("Verifying Element is Not Displayed : Element Found !");
            Assert.fail("Verifying Element is Not Displayed : Element Found !");
        }
        else
            System.out.println("Verifying Element is Not Displayed : Element Not Found !");
    }


    protected static void click(WebElement element)
    {
        Boolean found=false;
        WaitForElement(element);
        WaitForElementToBeClickable(element);
        //System.out.println("Clicking on Element :" + element.toString());
        for (int i = 0; i <defaultTimeout ; i++) {
            try {
                element.click();
                found=true;
                break;
            } catch (Exception e) {
                sleep(1);
            }
        }
        Assert.assertTrue(found==true, "Element '" + element.toString() + "'");
    }

    //This method takes a picture of the current screen when called and saves in the Screenshots folder
    protected static void captureScreenshot() {
        try {
            String filePath = "C:\\Solution\\PetStore\\target\\screenshots";
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Date date = new Date();
            String date1 = dateFormat.format(date);
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(filePath + "\\screenshot_" + date1 + ".png"));

            System.out.println("***Placed screen shot in " + filePath
                    + "\\screenshot_" + date1 + ".png" + " ***");
            Reporter.log("<a href='" + filePath + "\\screenshot_" + date1
                    + ".png" + "'>screenshot</a>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void WaitForPageRefresh()
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        for (int i=0; i<defaultTimeout; i++){
            if (js.executeScript("return document.readyState").toString().equals("complete")){
                break;
            }
            sleep(1);
        }
        sleep(6);
    }

    protected static void AssertElementText(WebElement element, String expected) {
        Log("Asserting Element Text: '"+ expected+"'");
        WaitForElement(element);
        String actual = getText(element);
        Assert.assertTrue(expected.equals(actual), "Assert Failed, Expected: '" + expected + ", Actual: '" + actual + "'");
    }

    protected static void Log(String text)
    {
        System.out.println(text);
    }

    protected static void sendKeys(WebElement element, String text) {
        //Log("Entering Text'"+text+"' in ths element '"+element+"'");
        WaitForElement(element);
        WaitForElementToBeClickable(element);
        try {
            element.clear();
            element.sendKeys(text);
        }
        catch (Exception e){
            sleep(5);
            element.clear();
            element.sendKeys(text);
        }
    }


    public void WaitForMessage(String messageText) {
        WebElement element;
        String displayedText = "";

        for (int i = defaultTimeout; i > 0; i--) {
            try {
                element = ElementByXPath(".//div[@class='outputmsg_div']//span[@class='outputmsg_text']");
                displayedText = getText(element);
                if (displayedText.equals(messageText))
                {
                    Log("WaitForMessage: Message displayed: '" + messageText +"'");
                    break;
                }
                else
                    sleep(1);
            } catch (Exception e) {
                Log("Exception Occurred in WaitForMessage, Error Details: " + e.getMessage());
            }
        }
    }

    public void initializeTest()
    {
        System.setProperty("webdriver.chrome.driver", "target/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
//        options.addArguments("--headless");
        driver =new ChromeDriver(options);
        driver.manage().window().maximize();
    }


    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }


    public List<String> GetVehicleRegistrations() throws FileNotFoundException {
        File inputfile = new File("C:\\car_input.txt");
        // initialize the scanner
        Scanner scaninputfile = new Scanner(inputfile);
        // iterate through the file line by line
        List<String> vehicleregarray = new ArrayList<String>();

        while(scaninputfile.hasNextLine()){
            // scan for names on the content of the file
            String str1 = scaninputfile.findInLine("[A-Z]{2}[0-9]{2}[A-Z]{3}") ;
            String str2 = scaninputfile.findInLine("[A-Z]{2}[0-9]{2}\\s[A-Z]{3}");
            if(str1 != null){
                vehicleregarray.add(str1);
            }

            if(str2 != null){

                vehicleregarray.add(str2);
            }
            // advance to the next line
            scaninputfile.nextLine();
        }
        // close the scanner object;
        scaninputfile.close();
        return vehicleregarray;
    }

    public void exitTest() {
            try {
                Log("Waiting for 2 Seconds before Capturing the Screenshot");
                sleep(2);
                //captureScreenshot();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Step 4- Close Driver
            driver.close();
            //Step 5- Quit Driver
            driver.quit();
    }
}

