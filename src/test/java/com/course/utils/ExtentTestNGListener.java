package com.course.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        //called once when suite starts
        ExtentReportManager.initReport();
        System.out.println("Extent Report Initialized1");
    }

    @Override
    public void onTestStart(ITestResult result) {
        //called when each test method starts
        ExtentReportManager.createTest(result.getName(), // getName = test method name
                result.getMethod().getDescription()); //Description: @Test(description)
        System.out.println("Starting: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // called when test passes
        ExtentReportManager.getTest().pass("Test Passed");
        System.out.println("Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.getTest().fail("Test Failed: " + result.getThrowable().getMessage());
        //result.getThrowable() = exception causing failure
        // getMessage = error msg
        System.out.println("Failed: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().skip("Test Skipped");
        System.out.println("Skipped Test: " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // called when the suite finishes
        ExtentReportManager.flushReport();
        System.out.println("Report saved to: reports/TestReport.html");
    }
}