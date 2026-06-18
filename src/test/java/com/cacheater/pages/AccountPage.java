package com.cacheater.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Automation Test Store account dashboard.
 * URL: /index.php?rt=account/account  (reached after successful login)
 */
public class AccountPage {

    private final WebDriver driver;

    private final By logoutLink = By.cssSelector("a[href*='rt=account/logout']");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoaded() {
        return driver.getCurrentUrl().contains("rt=account/account")
                && !driver.findElements(logoutLink).isEmpty();
    }

    public boolean isLogoutLinkVisible() {
        return !driver.findElements(logoutLink).isEmpty();
    }
}
