package com.course.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentReportManager {

    private static ExtentReports extent;

    // Stores the parent test for each TestNG <test>
    private static final Map<String, ExtentTest> testSuites = new ConcurrentHashMap<>();

    //Stores the individual test for the current thread
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    // ExtentTest = represents one test in the report

    public static synchronized void initReport(){

        //Prevent the report from being initialized more than once
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/TestReport.html");
        // ExtentSparkReporter - creates html report

        reporter.config().setTheme(Theme.DARK); //professional
        reporter.config().setDocumentTitle("Selenium Automation Report");
        reporter.config().setReportName("Selenium Test Report");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        // extent now knows where to write the HTML file

        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        extent.setSystemInfo("Tester", "Nazia");
        // adds system info section to the report
    }

    public static ExtentTest createTestSuite(String suiteName){
        // call at start of each test to create a test entry in report

        // if this TESTNG test already exists, return it
        if(testSuites.containsKey(suiteName)){
            return testSuites.get(suiteName);
        }
        ExtentTest suiteTest = extent.createTest(suiteName);
        testSuites.put(suiteName, suiteTest);
        return suiteTest;
    }

    public static  ExtentTest createTest(
            String suiteName,
            String testName,
            String description) {
        ExtentTest parent = testSuites.get(suiteName);

        if (parent == null) {
            parent = createTestSuite(suiteName);
        }

        // Create individual test underneath Smoke tests
        // or Regression tests
        ExtentTest extentTest = parent.createNode(testName, description);

        test.set(extentTest);
        return extentTest;
    }

    public static ExtentTest getTest(){
        return test.get();
    }

    public static void flushReport(){
        // call once at the end - writes everything to the HTML file
        if (extent != null) {
            extent.flush();
        }
    }
}
