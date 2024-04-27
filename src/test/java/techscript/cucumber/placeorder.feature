@tag
Feature: Place the order from ecommerce website
  Background:
    Given I landed on Ecommerce page

  @PlaceOrder
  Scenario Outline: Positive test for placing an order
    Given Logged in with username <name> and password <password>
    When I add product <productName> to cart
    And Checkout <productName> and submit the order
    Then "ORDER" message is displayed on Confirmation page
    Examples:
      | name          | password      | productName  |
      | ark@gmail.com | Password@123  | ADIDAS       |

