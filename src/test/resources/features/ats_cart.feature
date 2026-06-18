@automationstore @cart
Feature: Automation Test Store Shopping Cart
  As a shopper on Automation Test Store
  I want to add products to my cart from the Skincare category
  So that I can proceed to checkout

  Background:
    Given I have navigated to the Skincare product category

  @smoke
  Scenario: Category page lists products with add-to-cart buttons
    Then the category page should display at least one product

  @smoke
  Scenario: Adding one product increments the cart count to 1
    When I add product 65 to the cart
    Then the cart item count should be 1

  Scenario: Adding two different products increments the cart count to 2
    When I add product 65 to the cart
    And I add product 93 to the cart
    Then the cart item count should be 2

  Scenario: Added product appears in the cart page
    When I add product 65 to the cart
    And I navigate to the cart page
    Then the cart page should be loaded
    And the cart should not be empty
    And the cart should contain at least 1 item
