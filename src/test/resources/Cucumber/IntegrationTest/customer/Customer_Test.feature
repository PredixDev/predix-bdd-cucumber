Feature: Support to create/update/delete customer and query by customer ID number 
  As an Customer Modeler,
  I am able to create/update/delete customer and query by customer ID number 
 
Scenario: Create/update/delete customer

    Given a customer with name "Customer01" and phone number "(123)456-7890" _Customer_
    When a user queries the customer by customer id "Customer01" _Customer_
    Then the customer with name "Customer01" and phone number "(123)456-7890" should be returned _Customer_
    When a user updates the customer with name "Customer01" and phone number "(321)456-7891" _Customer_
    And a user queries the customer by customer id "Customer01" _Customer_
    Then the customer with name "Customer01" and phone number "(321)456-7891" should be returned _Customer_
    When a user deletes the customer "Customer01" _Customer_
    And a user queries the customer by customer id "Customer01" _Customer_
    Then the customer "Customer01" is not exist _Customer_
  
