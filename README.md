# Selenium E2E Framework

[![Selenium E2E Tests](https://github.com/Cacheater/selenium-e2e-framework/actions/workflows/selenium-tests.yml/badge.svg)](https://github.com/Cacheater/selenium-e2e-framework/actions/workflows/selenium-tests.yml)

End-to-end UI test framework built with **Java 17 · Selenium 4 · TestNG · Maven**,
following the **Page Object Model**. Tests run against the public
[SauceDemo](https://www.saucedemo.com/) application.

> Demo project showcasing how I structure maintainable UI automation — clear
> separation between pages and tests, headless CI, and report artifacts.

## What it demonstrates

- **Page Object Model** — locators and actions live in page classes, tests read like user intent.
- **Clean WebDriver lifecycle** — a `BaseTest` owns setup/teardown; tests stay focused on behaviour.
- **Zero-install drivers** — Selenium 4's Selenium Manager resolves the browser driver automatically.
- **CI on every push** — GitHub Actions runs the suite headless and uploads the TestNG report.
- **Parallel execution** — TestNG runs test classes in parallel.

## Project structure

```
selenium-e2e-framework/
├── pom.xml                  # Maven config & dependencies
├── testng.xml               # Suite definition (parallel)
├── .github/workflows/       # CI pipeline
└── src/test/java/com/cacheater/
    ├── base/   BaseTest.java        # WebDriver lifecycle
    ├── pages/  LoginPage.java       # Page Objects
    │           InventoryPage.java
    └── tests/  LoginTest.java       # E2E scenarios
```

## Requirements

- JDK 17+
- Maven 3.9+
- Google Chrome installed

## Run the tests

```bash
# Headless (default)
mvn test

# Watch the browser run
mvn test -Dheadless=false
```

Reports are generated under `target/surefire-reports/`.

## Scenarios covered

| Test | What it verifies |
|------|------------------|
| `validLoginShowsInventory` | A valid user reaches the products page and sees items |
| `lockedOutUserIsRejected`  | A locked-out user gets the correct error message |

## Next steps / ideas

- Add Allure reporting for rich HTML reports
- Cross-browser runs (Firefox, Edge) via a driver factory
- Cart and checkout flows as additional page objects

---
Maintained by [Fabián Solano (@Cacheater)](https://github.com/Cacheater) — QA Automation Engineer.
