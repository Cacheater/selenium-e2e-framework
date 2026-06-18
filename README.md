# Selenium E2E Framework

[![Selenium E2E Tests](https://github.com/Cacheater/selenium-e2e-framework/actions/workflows/selenium-tests.yml/badge.svg)](https://github.com/Cacheater/selenium-e2e-framework/actions/workflows/selenium-tests.yml)

End-to-end UI test framework built with **Java 21 · Selenium 4 · TestNG · Maven**,
following the **Page Object Model**. Tests run against two public demo applications:
[SauceDemo](https://www.saucedemo.com/) and [Automation Test Store](https://automationteststore.com/).

> Demo project showcasing how I structure maintainable UI automation — clear
> separation between pages and tests, headless CI, AJAX interactions, and report artifacts.

## What it demonstrates

- **Page Object Model** — locators and actions live in page classes, tests read like user intent.
- **Clean WebDriver lifecycle** — abstract base classes own setup/teardown; tests stay focused on behaviour.
- **Zero-install drivers** — Selenium 4's Selenium Manager resolves the browser driver automatically.
- **CI on every push** — GitHub Actions runs the suite headless and uploads the TestNG report.
- **Parallel execution** — TestNG runs test classes in parallel (`thread-count=4`).
- **AJAX interactions** — explicit waits for dynamic cart updates without page reload.
- **Multi-site coverage** — two independent base classes target different applications.

## Project structure

```
selenium-e2e-framework/
├── pom.xml                        # Maven config & dependencies
├── testng.xml                     # Suite definition (parallel, 3 test groups)
├── .github/workflows/             # CI pipeline
└── src/test/java/com/cacheater/
    ├── base/
    │   ├── BaseTest.java                    # WebDriver lifecycle — SauceDemo
    │   └── AutomationStoreBaseTest.java     # WebDriver lifecycle — Automation Test Store
    ├── pages/
    │   ├── LoginPage.java                   # SauceDemo login
    │   ├── InventoryPage.java               # SauceDemo product list
    │   ├── AutomationStoreLoginPage.java    # ATS login
    │   ├── AccountPage.java                 # ATS account dashboard
    │   ├── ProductCategoryPage.java         # ATS category listing + AJAX cart
    │   └── CartPage.java                    # ATS shopping cart
    └── tests/
        ├── LoginTest.java                   # SauceDemo auth scenarios
        ├── AutomationStoreLoginTest.java    # ATS auth scenarios
        └── AddToCartTest.java               # ATS cart flows
```

## Requirements

- JDK 21+
- Maven 3.9+
- Google Chrome installed

## Run the tests

```bash
# All tests, headless (default)
mvn test

# Watch the browser run
mvn test -Dheadless=false

# Single test method
mvn test -Dtest=AddToCartTest#addSingleProductUpdatesCartCount
```

Reports are generated under `target/surefire-reports/`.

## Scenarios covered

### SauceDemo (`https://www.saucedemo.com/`)

| Test | What it verifies |
|------|------------------|
| `validLoginShowsInventory` | Valid user reaches the products page and sees items |
| `lockedOutUserIsRejected`  | Locked-out user gets the correct error message |

### Automation Test Store (`https://automationteststore.com/`)

| Test | What it verifies |
|------|------------------|
| `validLoginLandsOnAccountPage` | Valid credentials redirect to the account dashboard |
| `invalidPasswordShowsError`    | Wrong password shows an error and stays on login page |
| `emptyCredentialsShowError`    | Empty submission triggers a validation error |
| `categoryPageShowsProducts`    | Skincare category lists products with add-to-cart buttons |
| `addSingleProductUpdatesCartCount` | Adding one product increments the cart counter to 1 |
| `addTwoProductsUpdatesCartCount`   | Adding two products increments the cart counter to 2 |
| `addedProductAppearsInCartPage`    | Added product appears on the cart page |

## Next steps / ideas

- Add Allure reporting for rich HTML reports
- Cross-browser runs (Firefox, Edge) via a driver factory
- Checkout flow as additional page objects
- Data-driven tests with TestNG `@DataProvider`

---
Maintained by [Fabián Solano (@Cacheater)](https://github.com/Cacheater) — QA Automation Engineer.
