package com.cacheater.context;

import org.openqa.selenium.WebDriver;

/**
 * Shared context injected into every step definition class by PicoContainer.
 * One instance is created per Cucumber scenario and torn down after @After hooks run.
 */
public class ScenarioContext {

    public static final String SAUCE_BASE_URL = "https://www.saucedemo.com/";
    public static final String ATS_BASE_URL   = "https://automationteststore.com/";

    private WebDriver driver;

    public ScenarioContext() {}

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
