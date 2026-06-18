package com.cacheater.tests;

import com.cacheater.base.AutomationStoreBaseTest;
import com.cacheater.pages.AccountPage;
import com.cacheater.pages.AutomationStoreLoginPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * End-to-end authentication tests for https://automationteststore.com/.
 *
 * Prerequisites: the account "cacheater_e2e" with password "Test@12345" must exist.
 * Register at /index.php?rt=account/create if the credentials are not yet active.
 */
public class AutomationStoreLoginTest extends AutomationStoreBaseTest {

    private static final String VALID_USER     = "cacheater_e2e";
    private static final String VALID_PASSWORD = "Test@12345";

    @Test(description = "Valid credentials redirect to the account dashboard")
    public void validLoginLandsOnAccountPage() {
        AccountPage account = new AutomationStoreLoginPage(driver)
                .loginAs(VALID_USER, VALID_PASSWORD);

        assertTrue(account.isLoaded(),
                "Account dashboard should load after successful login");
        assertTrue(account.isLogoutLinkVisible(),
                "Logout link should be visible when logged in");
    }

    @Test(description = "Wrong password shows an error and stays on the login page")
    public void invalidPasswordShowsError() {
        AutomationStoreLoginPage loginPage = new AutomationStoreLoginPage(driver)
                .loginExpectingFailure(VALID_USER, "wrongPassword!");

        assertTrue(loginPage.hasErrorMessage(),
                "An error alert should appear for invalid credentials");
    }

    @Test(description = "Empty credentials show a validation error")
    public void emptyCredentialsShowError() {
        AutomationStoreLoginPage loginPage = new AutomationStoreLoginPage(driver)
                .loginExpectingFailure("", "");

        assertTrue(loginPage.hasErrorMessage(),
                "An error alert should appear when submitting empty fields");
    }
}
