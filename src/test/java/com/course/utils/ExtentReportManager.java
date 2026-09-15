package com.course.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    // important for parallel execution later
    // ExtentTest = represents one test in the report

    public static void initReport(){
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/TestReport.html");
        // ExtentSparkReporter - creates html report

        reporter.config().setTheme(Theme.DARK); //professional
        reporter.config().setDocumentTitle("Selenium Automation Report");
        reporter.config().setReportName("Login Test Suite");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        // extent now knows where to write the HTML file

        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        extent.setSystemInfo("Tester", "Nazia");
        // adds system info section to the report
    }

    public static ExtentTest createTest(String testName, String description){
        // call at start of each test to create a test entry in report
        ExtentTest extentTest = extent.createTest(testName, description);
        test.set(extentTest);
        return extentTest;
    }

    public static  ExtentTest getTest(){
        return test.get();
    }

    public static void flushReport(){
        // call once at the end - writes everything to the HTML file
        if (extent != null) {
            extent.flush();
        }
    }
}
