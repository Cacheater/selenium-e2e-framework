@automationstore @login
Feature: Automation Test Store Authentication
  As a registered customer on Automation Test Store
  I want to log in with my credentials
  So that I can access my account

  Background:
    Given I am on the Automation Test Store login page

  @smoke
  Scenario: Valid credentials redirect to the account dashboard
    When I log in with username "cacheater_e2e" and password "Test@12345"
    Then the account dashboard should be loaded
    And the logout link should be visible

  @negative
  Scenario: Wrong password shows an error and stays on the login page
    When I attempt to log in with username "cacheater_e2e" and password "wrongPassword!"
    Then an error message should be displayed on the login page

  @negative
  Scenario: Empty credentials show a validation error
    When I attempt to log in with username "" and password ""
    Then an error message should be displayed on the login page
