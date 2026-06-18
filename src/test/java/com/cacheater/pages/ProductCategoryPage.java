package com.cacheater.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for a product category listing on Automation Test Store.
 *
 * Example URL: /index.php?rt=product/category&path=36  (Makeup)
 *
 * Add-to-cart is AJAX-driven: clicking a.productcart POSTs the item and
 * updates the cart-count span in the top navigation without a full page reload.
 */
public class ProductCategoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Top-nav cart count — <span class="label"> inside the cart dropdown toggle.
    // The anchor also contains an <i> icon before the spans, so :first-child does NOT
    // match the count span; the .label class targets it precisely.
    private final By cartCountSpan = By.cssSelector(".topcart .dropdown-toggle span.label");

    public ProductCategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /** Navigate to a category by its path ID (e.g. 43 = Skincare). */
    public ProductCategoryPage navigateTo(int categoryPath) {
        driver.get("https://automationteststore.com/index.php?rt=product/category&path=" + categoryPath);
        // Wait until at least one add-to-cart button is present; handles CI where JS renders after readyState=complete
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.productcart")));
        return this;
    }

    /** Returns the number of add-to-cart buttons visible on the page. */
    public int productCount() {
        return driver.findElements(By.cssSelector("a.productcart")).size();
    }

    /**
     * Clicks the add-to-cart button for the given product ID and waits for the
     * cart-count span to reflect the addition.
     *
     * @param productId the data-id value on the a.productcart element
     * @return the updated cart item count shown in the header
     */
    public int addProductToCart(int productId) {
        String previousCount = driver.findElement(cartCountSpan).getText().trim();

        WebElement addButton = driver.findElement(
                By.cssSelector("a.productcart[data-id='" + productId + "']"));
        addButton.click();

        // Wait until the cart count span text changes from its previous value
        wait.until(d -> !d.findElement(cartCountSpan).getText().trim().equals(previousCount));

        String updatedText = driver.findElement(cartCountSpan).getText().trim();
        // The span may show "1 item(s)" or just "1"; extract the leading number
        return Integer.parseInt(updatedText.replaceAll("\\D.*", "").trim());
    }

    public int getCartCount() {
        String text = driver.findElement(cartCountSpan).getText().trim();
        return Integer.parseInt(text.replaceAll("\\D.*", "").trim());
    }
}
