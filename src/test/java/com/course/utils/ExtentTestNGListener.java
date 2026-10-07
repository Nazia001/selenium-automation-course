package com.course.utils;

import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGListener implements ITestListener, IExecutionListener {

    @Override
    public void onExecutionStart() {
        //called once before the complete TestNG execution
        ExtentReportManager.initReport();
        System.out.println("Extent Report Initialized");
    }

    @Override
    public void onStart(ITestContext context) {
        //this is the name from <test name = "Smoke Tests or Regression Tests"
        ExtentReportManager.createTestSuite(context.getName());
        System.out.println("Starting TestNG Test: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String suiteName =
                result.getTestContext().getName();
        String testName = result.getName();
        String description = result.getMethod().getDescription();
        ExtentReportManager.createTest(
                suiteName,
                testName,
                description
        );
        System.out.println("Starting: " + testName + " [" + suiteName + "]");
    }

    @Override
    public void onTestSuccess(ITestResult result){
        ExtentReportManager.getTest().pass("Test Passed");
        System.out.println("Passed " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String errorMsg = result.getThrowable() != null
                ? result.getThrowable().getMessage(): "Unknown error";
        ExtentReportManager.getTest().fail("Test Failed: " + errorMsg);
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
        // called after the entire TestNG execution
        ExtentReportManager.flushReport();
        System.out.println("Report saved to: reports/TestReport.html");
    }
}