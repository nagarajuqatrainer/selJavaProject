package hooks;




import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import base.DriverFactory;
import io.cucumber.java.*;
import reports.ExtentReportManager;
import utils.Screenshot;
import utils.ScreenshotUtil;

public class Hooks {

    private static ExtentReports extent;
    private static ExtentTest scenarioTest;

    @BeforeAll
    public static void before_all() {
        extent = ExtentReportManager.getInstance();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        scenarioTest = extent.createTest(scenario.getName());
   
    }

    
    
    @AfterStep
    public void afterStep(Scenario scenario) {
        try {
            // Capture screenshot
            String screenshotPath = ScreenshotUtil.captureScreenshot(DriverFactory.getDriver(), scenario.getName());

            if (scenario.isFailed()) {
                scenarioTest.log(Status.FAIL, "Step failed")
                            .addScreenCaptureFromPath(screenshotPath);
            } else {
                scenarioTest.log(Status.PASS, "Step passed")
                            .addScreenCaptureFromPath(screenshotPath);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @After
//    public void afterScenario(Scenario scenario) {
//    	
//    
//        if (scenario.isFailed()) {
//            scenarioTest.log(Status.FAIL, "Scenario failed: " + scenario.getName());
//        } else {
//            scenarioTest.log(Status.PASS, "Scenario passed: " + scenario.getName());
//        }
//    }

  

    @After
    public void tearDown(Scenario scenario) throws IOException {
    	
    	if (scenario.isFailed()) {
    	    File screenshotFile = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
    	    String screenshotPath = "target/screenshots/" + scenario.getName() + ".png";
    	    File destFile = new File(screenshotPath);
    	    FileUtils.copyFile(screenshotFile, destFile);

    	    ExtentCucumberAdapter.addTestStepLog("Scenario Failed: " + scenario.getName());
    	    ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(destFile.getAbsolutePath());
    	}
    }

    
    
    @AfterAll
    public static void after_all() {
        extent.flush(); 
    }

    @After
    public void closeBrowser() {
        DriverFactory.quitDriver();
    }
}
