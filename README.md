# Selenium Java Automation Framework

A structured **Selenium WebDriver automation framework** built with Java and Maven, developed as a hands-on QA automation project.

The project demonstrates progressive automation practices, from WebDriver fundamentals and JUnit 5 to **TestNG, Page Object Model, test grouping, data-driven testing, automated screenshots, Extent Reports, and CI/CD concepts**.

The goal is to demonstrate practical, maintainable test automation skills through a realistic and continuously evolving QA automation framework.

## 🛠️ Tech Stack

| Technology                 | Purpose                                       |
| -------------------------- | --------------------------------------------- |
| **Java 25**                | Programming language                          |
| **Selenium WebDriver 4**   | Web browser automation                        |
| **JUnit 5**                | Test automation and framework fundamentals    |
| **TestNG**                 | Test execution, grouping and suite management |
| **Maven**                  | Build and dependency management               |
| **WebDriverManager**       | Automated browser driver management           |
| **Extent Reports**         | HTML test execution reporting                 |
| **GitHub Actions**         | CI/CD automation                              |
| **Docker + Selenium Grid** | Distributed and parallel test execution       |
| **Git & GitHub**           | Version control and project collaboration     |

## 📁 Project Structure

```text
src/test/java/com/course/
├── FirstTest.java
│   └── Day 2: WebDriver lifecycle fundamentals
│
├── LocatorsTest.java
│   └── Day 3: Selenium locator strategies
│
├── ElementInteractionsTest.java
│   └── Day 4: Web element interactions
│
├── WaitsTest.java
│   └── Day 5: Synchronisation and wait strategies
│
└── LoginTest.java
    └── Day 6: End-to-end login automation
```

Additional framework configuration includes:

```text
pom.xml
testng.xml
.gitignore
```

## 🧪 Test Automation Coverage

### Selenium Fundamentals

* WebDriver lifecycle management
* Browser navigation
* Element identification
* Web element interactions
* Dropdowns
* Checkboxes and radio buttons
* JavaScript/browser alerts

### Locator Strategies

* `id`
* `name`
* `className`
* `cssSelector`
* `xpath`

### Synchronisation

* Implicit Wait
* Explicit Wait
* Fluent Wait
* Understanding why `Thread.sleep()` is an anti-pattern in maintainable automation

### Login Test Scenarios

* Successful login
* Invalid username
* Invalid password
* Empty field validation

### TestNG

* Test grouping
* Test suite configuration
* Suite-based execution
* Listener-based reporting

### Reporting & Evidence

* Extent Reports
* HTML test reports
* Automated screenshots for failed tests

## 🏗️ Framework Practices

This project focuses on building automation that is **maintainable, reusable and scalable**, rather than simply automating individual test cases.

Key practices demonstrated include:

* Page Object Model
* Reusable automation components
* Test grouping
* Data-driven testing
* Separation of test configuration and execution
* Failure evidence through screenshots
* HTML reporting
* Maven-based test execution

## 📊 Test Execution & Reporting

Tests can be executed through Maven or the configured TestNG suite.

Extent Reports provide an HTML-based execution report, while screenshots provide additional evidence when tests fail.

This combination makes test failures easier to investigate and provides a clear overview of test execution results.

## 🚀 How to Run

### Prerequisites

Make sure the following are installed:

* Java 25+
* Maven 3.9+
* Google Chrome
* Git

### Clone the Repository

```bash
git clone https://github.com/Nazia001/selenium-automation-course.git
cd selenium-automation-course
```

### Run the Test Suite

```bash
mvn clean test
```

### Run a Specific Test Class

```bash
mvn test -Dtest=LoginTest
```

### Run a Specific Test Method

```bash
mvn test -Dtest=LoginTest#successfulLoginTest
```

## 📸 Test Evidence

Failed test screenshots are captured automatically and stored in the project's `screenshots/` directory.

HTML execution reports are generated through Extent Reports.

## 🔄 CI/CD & Scalable Execution

The project also explores modern automation execution practices, including:

* GitHub Actions for automated test execution
* Docker-based execution
* Selenium Grid
* Parallel browser test execution

These components demonstrate how UI automation can be integrated into a modern CI/CD workflow.

## 📈 Project Progression

The framework is being developed incrementally as new QA automation concepts are introduced.

```text
Selenium Fundamentals
        ↓
JUnit 5
        ↓
TestNG
        ↓
Page Object Model
        ↓
Data-Driven Testing
        ↓
Test Grouping & Suites
        ↓
Extent Reports
        ↓
Screenshots & Test Evidence
        ↓
CI/CD & Distributed Execution
```

## 👩‍💻 Author

**Nazia**
QA Automation Engineer | ISTQB Certified | AWS Certified

This project is part of my ongoing QA automation learning and portfolio development, with a focus on building practical Selenium automation skills and demonstrating industry-relevant testing practices.

[LinkedIn](https://www.linkedin.com/in/nazia-hasin-7623315b/)
