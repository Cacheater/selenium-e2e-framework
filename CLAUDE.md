# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run all tests (headless Chrome, default)
mvn test

# Run with visible browser
mvn test -Dheadless=false

# Run a single test method
mvn test -Dtest=AddToCartTest#addSingleProductUpdatesCartCount

# Compile without running tests
mvn compile
```

Reports are generated under `target/surefire-reports/`.

## Architecture

Java 17 · Selenium 4 · TestNG · Maven framework targeting two sites:
- [SauceDemo](https://www.saucedemo.com/) — original suite
- [Automation Test Store](https://automationteststore.com/) — extended suite

**Layer structure** (`src/test/java/com/cacheater/`):

- `base/BaseTest.java` — Abstract base for SauceDemo tests. Owns `WebDriver` lifecycle via `@BeforeMethod`/`@AfterMethod`. Reads the `headless` system property to toggle Chrome headless mode. Sets a 10s implicit wait and navigates to `BASE_URL` before each test.
- `base/AutomationStoreBaseTest.java` — Abstract base for Automation Test Store tests. Same lifecycle pattern as `BaseTest` but targets `https://automationteststore.com/`.
- `pages/` — Page Object classes. Each page receives `WebDriver` via constructor, keeps locators private, and exposes user-intent methods. `LoginPage.loginAs()` returns an `InventoryPage` on success (fluent navigation pattern). Automation Test Store pages: `AutomationStoreLoginPage`, `AccountPage`, `ProductCategoryPage`, `CartPage`.
- `tests/` — Test classes extend the appropriate base and compose page objects directly. No page object instantiation in base classes; tests wire pages themselves.

**Parallelism:** `testng.xml` runs test classes in parallel (`parallel="classes"`, `thread-count="2"`). Each test method gets its own `WebDriver` instance (per `@BeforeMethod` scope), so tests are isolated.

**Driver resolution:** Selenium Manager (bundled in Selenium 4) auto-downloads the matching ChromeDriver — no manual driver management needed.

**CI:** GitHub Actions runs on push/PR to `main` using headless Chrome and uploads `target/surefire-reports/` as an artifact.

## Conventions

- New page objects go in `pages/`, extend nothing, accept `WebDriver` in constructor.
- New test classes go in `tests/`, extend the appropriate base (`BaseTest` for SauceDemo, `AutomationStoreBaseTest` for Automation Test Store), and must be registered in `testng.xml` to run in CI.
- Use `By` fields (private, final) for all locators; prefer `data-test` attributes over CSS class selectors.

## Automation Test Store — known quirks

**AJAX cart vs. navigation:** Product tiles on category pages have `a.productcart` buttons. The site's JS only triggers the AJAX add-to-cart when `href="#"`; buttons with a full product URL navigate to the product detail page instead. Use categories whose products have `href="#"`:

| Category | path param | Known AJAX product IDs |
|---|---|---|
| Skincare | `43` | 65, 66, 67, 68, 93 |
| Makeup › Lips | `36_41` | 108 |

Makeup (path=`36`) products use full hrefs — **do not use them for cart tests**.

**Cart count locator:** `.topcart .dropdown-toggle span.label` — the `<span class="label label-orange font14">` inside the header cart dropdown. The AJAX handler updates its text to the new item count.

**Test account:** `cacheater_e2e` / `Test@12345` — registered at `https://automationteststore.com/index.php?rt=account/create`. Used by `AutomationStoreLoginTest`. Do not delete or change the password.
