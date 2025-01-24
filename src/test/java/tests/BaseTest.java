package tests;

import io.qameta.allure.Allure;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/************************************************************************
 Description : BaseTest class
 Created by : Tomasz Zulawnik

 Class History
 -------------------------------------------------------------------------
 Date 		Author		 							    Reason
 2022-02-02	Tomasz Zulawnik                             Class created
 ************************************************************************/

public class BaseTest {

    protected WebDriver driver;

    protected static String environment;
    protected static boolean externalDisplay;
    protected static boolean headless;
    protected static int displayXAxis;
    protected static int displayYAxis;
    protected static String baseUrl;
    protected static String communityUrl;
    protected static String adminUsername;
    protected static String adminPassword;
    protected static String userUsername;
    protected static String userPassword;
    protected static String communityUserUsername;
    protected static String communityUserPassword;

    private final static String ENVIRONMENT_LOCATION_PROPERTY_NAME = "envLocation";
    private static String environmentLocation;

    @BeforeSuite
    public void suiteSetup(){
        environmentLocation = System.getProperty(ENVIRONMENT_LOCATION_PROPERTY_NAME);
        getEnvironmentData();
    }

    @BeforeClass
    public void mainSetup() {

        //block notifications popup
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        //headless setup
        if (headless) {
            options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--window-size=1920,1040", "--ignore-certificate-errors");
        }

        driver = new ChromeDriver(options);

        //set window position e.g. for external monitors
        if (externalDisplay) {
            driver.manage().window().setPosition(new Point(displayXAxis, displayYAxis));
        }

        //maximize browser window
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod(ITestResult testResult) {

        //Add print screen to allure
        if (testResult.getStatus() == ITestResult.FAILURE) {
            String pathToPNG = takeScreenshot();
            Path content = Paths.get(pathToPNG);
            try (InputStream is = Files.newInputStream(content)) {
                Allure.addAttachment("Print screen", is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Delete unnecessary files
            try {
                Files.deleteIfExists(Paths.get(pathToPNG));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    public void classTearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    public void getEnvironmentData() {
        JSONParser jsonParser = new JSONParser();
        try {
            Object object = jsonParser.parse(new FileReader(environmentLocation));
            JSONObject jsonObject = (JSONObject) object;
            environment = (String) jsonObject.get("environment");
            externalDisplay = (boolean) jsonObject.get("externalDisplay");
            headless = (boolean) jsonObject.get("headless");
            displayXAxis = ((Long) jsonObject.get("windowXAxis")).intValue();
            displayYAxis = ((Long) jsonObject.get("windowYAxis")).intValue();
            baseUrl = (String) jsonObject.get("baseUrl");
            communityUrl = (String) jsonObject.get("communityUrl");
            adminUsername = (String) jsonObject.get("adminUsername");
            adminPassword = (String) jsonObject.get("adminPassword");
            userUsername = (String) jsonObject.get("userUsername");
            userPassword = (String) jsonObject.get("userPassword");
            communityUserUsername = (String) jsonObject.get("communityUserUsername");
            communityUserPassword = (String) jsonObject.get("communityUserPassword");
        } catch (IOException | ParseException e) {
            System.out.println("Cannot read environment.json file.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String takeScreenshot() {
        String pathToDirectory = "target/screenshots";
        String pathToFile = pathToDirectory + "/" + java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS")) + "_error.png";
        File directory = new File(pathToDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(screenshot, new File(pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathToFile;
    }
}
