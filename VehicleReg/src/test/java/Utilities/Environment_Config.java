package Utilities;

public class Environment_Config {

    protected static String Environment = "TEST";    //TEST OR PREPROD

    protected static String TEST_URL = "https://cartaxcheck.co.uk/";

    protected static String PREPROD_URL = "https://cartaxcheck.co.uk/";


    public static String getURL()
    {
        if (Environment == "TEST") {
            return TEST_URL;
        } else if (Environment == "PREPROD") {
            return PREPROD_URL;
        }
        return TEST_URL;
    }

}
