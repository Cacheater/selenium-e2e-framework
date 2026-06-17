package com.cacheater.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the product inventory shown after a successful login.
 */
public class InventoryPage {

    private final WebDriver driver;

    private final By title         = By.cssSelector(".title");
    private final By inventoryList = By.cssSelector(".inventory_item");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoaded() {
        return driver.findElements(title).size() > 0
                && driver.findElement(title).getText().equalsIgnoreCase("Products");
    }

    public int productCount() {
        return driver.findElements(inventoryList).size();
    }
}
