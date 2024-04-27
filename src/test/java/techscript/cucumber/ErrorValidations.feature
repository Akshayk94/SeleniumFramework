@tag
Feature: Error validation
  @Regression
  Scenario Outline: Negative test for placing an order
    Given I landed on Ecommerce page
    When Logged in with username <name> and password <password>
    Then "Incorrect email or password." message is displayed
    Examples:
      | name          | password      |
      | ak@gmail.com  | Password@123  |

