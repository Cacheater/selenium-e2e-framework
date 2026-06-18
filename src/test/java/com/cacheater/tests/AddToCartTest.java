package com.cacheater.tests;

import com.cacheater.base.AutomationStoreBaseTest;
import com.cacheater.pages.CartPage;
import com.cacheater.pages.ProductCategoryPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * End-to-end cart tests for https://automationteststore.com/.
 *
 * Uses the Skincare category (path=43) — products here have href="#" on their
 * add-to-cart buttons, which triggers the site's AJAX cart update rather than
 * navigating to the product page (Makeup category products use a full href).
 */
public class AddToCartTest extends AutomationStoreBaseTest {

    // Skincare category path
    private static final int SKINCARE_CATEGORY = 43;

    // Absolue Eye Precious Cells
    private static final int EYE_CREAM_ID = 65;

    // Creme Precieuse Nuit 50ml
    private static final int NIGHT_CREAM_ID = 93;

    @Test(description = "Category page lists products with add-to-cart buttons")
    public void categoryPageShowsProducts() {
        ProductCategoryPage page = new ProductCategoryPage(driver)
                .navigateTo(SKINCARE_CATEGORY);

        assertTrue(page.productCount() > 0,
                "Makeup category should list at least one product");
    }

    @Test(description = "Adding one product increments the cart count to 1")
    public void addSingleProductUpdatesCartCount() {
        ProductCategoryPage page = new ProductCategoryPage(driver)
                .navigateTo(SKINCARE_CATEGORY);

        int cartCount = page.addProductToCart(EYE_CREAM_ID);

        assertEquals(cartCount, 1,
                "Cart should contain exactly 1 item after adding one product");
    }

    @Test(description = "Adding two different products increments the cart count to 2")
    public void addTwoProductsUpdatesCartCount() {
        ProductCategoryPage page = new ProductCategoryPage(driver)
                .navigateTo(SKINCARE_CATEGORY);

        page.addProductToCart(EYE_CREAM_ID);
        int cartCount = page.addProductToCart(NIGHT_CREAM_ID);

        assertEquals(cartCount, 2,
                "Cart should contain 2 items after adding two products");
    }

    @Test(description = "Cart page shows the added product after navigating to it")
    public void addedProductAppearsInCartPage() {
        new ProductCategoryPage(driver)
                .navigateTo(SKINCARE_CATEGORY)
                .addProductToCart(EYE_CREAM_ID);

        CartPage cart = new CartPage(driver).open();

        assertTrue(cart.isLoaded(), "Cart page should load");
        assertFalse(cart.isEmpty(), "Cart should not be empty after adding a product");
        assertTrue(cart.itemCount() >= 1, "Cart should list at least one product row");
    }
}
