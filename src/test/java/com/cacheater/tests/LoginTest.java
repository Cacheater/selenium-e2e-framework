package com.cacheater.tests;

import com.cacheater.base.BaseTest;
import com.cacheater.pages.InventoryPage;
import com.cacheater.pages.LoginPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * End-to-end checks for the authentication flow.
 */
public class LoginTest extends BaseTest {

    @Test(description = "A valid user lands on the products page")
    public void validLoginShowsInventory() {
        InventoryPage inventory = new LoginPage(driver)
                .loginAs("standard_user", "secret_sauce");

        assertTrue(inventory.isLoaded(), "Inventory page should load after login");
        assertTrue(inventory.productCount() > 0, "Inventory should list products");
    }

    @Test(description = "A locked-out user is rejected with a clear error")
    public void lockedOutUserIsRejected() {
        LoginPage login = new LoginPage(driver);
        login.loginAs("locked_out_user", "secret_sauce");

        assertEquals(
                login.getErrorMessage(),
                "Epic sadface: Sorry, this user has been locked out.",
                "Locked-out user should see the lockout error");
    }
}
