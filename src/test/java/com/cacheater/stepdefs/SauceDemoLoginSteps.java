package com.cacheater.stepdefs;

import com.cacheater.context.ScenarioContext;
import com.cacheater.pages.InventoryPage;
import com.cacheater.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SauceDemoLoginSteps {

    private final ScenarioContext context;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    public SauceDemoLoginSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("I am on the SauceDemo login page")
    public void iAmOnTheSauceDemoLoginPage() {
        WebDriver driver = context.getDriver();
        driver.get(ScenarioContext.SAUCE_BASE_URL);
        loginPage = new LoginPage(driver);
    }

    @When("I log in as {string} with password {string}")
    public void iLogInAs(String username, String password) {
        inventoryPage = loginPage.loginAs(username, password);
    }

    @Then("the inventory page should be loaded")
    public void theInventoryPageShouldBeLoaded() {
        assertTrue(inventoryPage.isLoaded(), "Inventory page should load after valid login");
    }

    @And("the inventory should display at least one product")
    public void theInventoryShouldDisplayAtLeastOneProduct() {
        assertTrue(inventoryPage.productCount() > 0, "Inventory should list at least one product");
    }

    @Then("I should see the error message {string}")
    public void iShouldSeeTheErrorMessage(String expectedMessage) {
        assertEquals(loginPage.getErrorMessage(), expectedMessage,
            "Locked-out user should see the expected error message");
    }
}
