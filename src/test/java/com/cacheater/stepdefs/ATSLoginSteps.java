package com.cacheater.stepdefs;

import com.cacheater.context.ScenarioContext;
import com.cacheater.pages.AccountPage;
import com.cacheater.pages.AutomationStoreLoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertTrue;

public class ATSLoginSteps {

    private final ScenarioContext context;
    private AutomationStoreLoginPage loginPage;
    private AccountPage accountPage;

    public ATSLoginSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("I am on the Automation Test Store login page")
    public void iAmOnTheAutomationTestStoreLoginPage() {
        // Constructor navigates to the login URL internally
        loginPage = new AutomationStoreLoginPage(context.getDriver());
    }

    @When("I log in with username {string} and password {string}")
    public void iLogInWithUsernameAndPassword(String username, String password) {
        accountPage = loginPage.loginAs(username, password);
    }

    @When("I attempt to log in with username {string} and password {string}")
    public void iAttemptToLogInWithUsernameAndPassword(String username, String password) {
        loginPage.loginExpectingFailure(username, password);
    }

    @Then("the account dashboard should be loaded")
    public void theAccountDashboardShouldBeLoaded() {
        assertTrue(accountPage.isLoaded(), "Account dashboard should load after successful login");
    }

    @And("the logout link should be visible")
    public void theLogoutLinkShouldBeVisible() {
        assertTrue(accountPage.isLogoutLinkVisible(), "Logout link should be visible when logged in");
    }

    @Then("an error message should be displayed on the login page")
    public void anErrorMessageShouldBeDisplayedOnTheLoginPage() {
        assertTrue(loginPage.hasErrorMessage(), "An error alert should appear for invalid credentials");
    }
}
