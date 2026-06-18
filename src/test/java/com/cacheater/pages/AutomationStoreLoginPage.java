package com.cacheater.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the Automation Test Store login screen.
 * URL: /index.php?rt=account/login
 *
 * The site runs OpenCart; form fields use the standard OpenCart IDs
 * (loginFrm_loginname / loginFrm_password).
 */
public class AutomationStoreLoginPage {

    private final WebDriver driver;

    private final By loginNameInput = By.id("loginFrm_loginname");
    private final By passwordInput  = By.id("loginFrm_password");
    private final By loginButton    = By.cssSelector("#loginFrm button[type='submit']");
    private final By errorAlert     = By.cssSelector(".alert-danger, .alert.alert-error");

    public AutomationStoreLoginPage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://automationteststore.com/index.php?rt=account/login");
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(loginNameInput));
    }

    public AccountPage loginAs(String loginName, String password) {
        driver.findElement(loginNameInput).clear();
        driver.findElement(loginNameInput).sendKeys(loginName);
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("rt=account/account"));
        return new AccountPage(driver);
    }

    /** Submits the form and waits for an error alert to appear (used for invalid-credential tests). */
    public AutomationStoreLoginPage loginExpectingFailure(String loginName, String password) {
        driver.findElement(loginNameInput).clear();
        driver.findElement(loginNameInput).sendKeys(loginName);
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(errorAlert),
                        ExpectedConditions.urlContains("rt=account/account")));
        return this;
    }

    public boolean hasErrorMessage() {
        return !driver.findElements(errorAlert).isEmpty();
    }

    public String getErrorMessage() {
        return driver.findElement(errorAlert).getText();
    }
}
