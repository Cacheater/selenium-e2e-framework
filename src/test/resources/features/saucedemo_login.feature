@saucedemo @login
Feature: SauceDemo Authentication
  As a shopper on SauceDemo
  I want to log in with my credentials
  So that I can reach the product inventory

  Background:
    Given I am on the SauceDemo login page

  @smoke
  Scenario: Valid user lands on the products page
    When I log in as "standard_user" with password "secret_sauce"
    Then the inventory page should be loaded
    And the inventory should display at least one product

  @negative
  Scenario: Locked-out user is rejected with a clear error
    When I log in as "locked_out_user" with password "secret_sauce"
    Then I should see the error message "Epic sadface: Sorry, this user has been locked out."
