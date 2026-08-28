# Selenium Java Automation Framework

A structured Selenium WebDriver automation course project built with Java,
Maven, and JUnit 5 — covering locators, interactions, waits, Page Object
Model, CI/CD with GitHub Actions, and Docker + Selenium Grid.

## 🛠️ Tech Stack

| Tool | Purpose |
|------|---------|
| Java 25 | Programming language |
| Selenium WebDriver 4 | Browser automation |
| JUnit 5 | Test framework |
| Maven | Build and dependency management |
| WebDriverManager | Automatic driver management |
| GitHub Actions | CI/CD pipeline  |
| Docker + Selenium Grid | Parallel execution  |

## 📁 Project Structure
```
src/test/java/com/course/
├── FirstTest.java               # Day 2: WebDriver lifecycle basics
├── LocatorsTest.java            # Day 3: All locator strategies
├── ElementInteractionsTest.java # Day 4: Click, type, dropdown, alerts
├── WaitsTest.java               # Day 5: Implicit, Explicit, FluentWait
└── LoginTest.java               # Day 6: Mini project - E2E login com.course.tests
```

## ✅ Test Coverage

- **Locators**: id, name, className, cssSelector, xpath
- **Interactions**: click, sendKeys, dropdowns, checkboxes, radio buttons, alerts
- **Waits**: Thread.sleep (anti-pattern), ImplicitWait, ExplicitWait, FluentWait
- **Login scenarios**: successful login, wrong password, wrong username, empty fields
- **Screenshots**: auto-captured on test failure

## 🚀 How to Run

### Prerequisites
- Java 25+
- Maven 3.9+
- Chrome browser

### Run all com.course.tests
```bash
mvn clean test
```

### Run a specific test class
```bash
mvn test -Dtest=LoginTest
```

### Run a specific test method
```bash
mvn test -Dtest=LoginTest#successfulLoginTest
```

## 📸 Screenshots

Failed test screenshots are saved automatically to the `screenshots/` folder.


## 👩‍💻 Author

**Nazia** — QA Automation Engineer  
ISTQB Certified | AWS Certified | Selenium Java  
[LinkedIn](https://www.linkedin.com/in/nazia-hasin-7623315b/)