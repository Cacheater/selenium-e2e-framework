package com.cacheater.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Automation Test Store shopping cart.
 * URL: /index.php?rt=checkout/cart
 */
public class CartPage {

    private final WebDriver driver;

    private final By cartRows      = By.cssSelector(".product-list tbody tr, #cart_list tr");
    private final By emptyMessage  = By.cssSelector(".contentpanel p");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public CartPage open() {
        driver.get("https://automationteststore.com/index.php?rt=checkout/cart");
        return this;
    }

    public boolean isLoaded() {
        return driver.getCurrentUrl().contains("rt=checkout/cart");
    }

    /** Number of product rows in the cart table. */
    public int itemCount() {
        return driver.findElements(cartRows).size();
    }

    public boolean isEmpty() {
        return driver.findElements(emptyMessage).stream()
                .anyMatch(el -> el.getText().toLowerCase().contains("empty"));
    }
}
