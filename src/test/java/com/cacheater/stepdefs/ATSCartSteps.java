package com.cacheater.stepdefs;

import com.cacheater.context.ScenarioContext;
import com.cacheater.pages.CartPage;
import com.cacheater.pages.ProductCategoryPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ATSCartSteps {

    private static final int SKINCARE_CATEGORY = 43;

    private final ScenarioContext context;
    private ProductCategoryPage categoryPage;
    private CartPage cartPage;
    private int lastCartCount;

    public ATSCartSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("I have navigated to the Skincare product category")
    public void iHaveNavigatedToTheSkincareProductCategory() {
        categoryPage = new ProductCategoryPage(context.getDriver())
                .navigateTo(SKINCARE_CATEGORY);
    }

    @Then("the category page should display at least one product")
    public void theCategoryPageShouldDisplayAtLeastOneProduct() {
        assertTrue(categoryPage.productCount() > 0,
            "Skincare category should list at least one product with an add-to-cart button");
    }

    @When("I add product {int} to the cart")
    public void iAddProductToTheCart(int productId) {
        lastCartCount = categoryPage.addProductToCart(productId);
    }

    @Then("the cart item count should be {int}")
    public void theCartItemCountShouldBe(int expectedCount) {
        assertEquals(lastCartCount, expectedCount,
            "Cart item count should match after adding products");
    }

    @And("I navigate to the cart page")
    public void iNavigateToTheCartPage() {
        cartPage = new CartPage(context.getDriver()).open();
    }

    @Then("the cart page should be loaded")
    public void theCartPageShouldBeLoaded() {
        assertTrue(cartPage.isLoaded(), "Cart page should load successfully");
    }

    @Then("the cart should not be empty")
    public void theCartShouldNotBeEmpty() {
        assertFalse(cartPage.isEmpty(), "Cart should not be empty after adding a product");
    }

    @Then("the cart should contain at least {int} item")
    public void theCartShouldContainAtLeastItem(int minItems) {
        assertTrue(cartPage.itemCount() >= minItems,
            "Cart should list at least " + minItems + " product row(s)");
    }
}
