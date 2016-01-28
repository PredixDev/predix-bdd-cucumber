package com.ge.predix.labs.integrationtest.cucumber.customer;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.ge.ams.dto.Customer;
import com.ge.predix.labs.common.JsonMapper;
import com.ge.predix.labs.integrationtest.RestTestBase;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CustomerTest extends RestTestBase {

	private Customer customer = new Customer();

	@Given("^a customer with name \"([^\"]*)\" and phone number \"([^\"]*)\" _Customer_$")
	public void a_customer_with_name_and_phone_number__Customer_(
			String customerName, String customerPhone) throws Throwable {
		customer.setAssetId(customerName);
		customer.setPhone(customerPhone);
		customer.setUri("/customer/" + customerName);
        List<Customer> list = new ArrayList<>();
        list.add(customer);
		rest.post(config.paths.prefix + "/customer", JsonMapper.toJson(list));
	}

	@When("^a user queries the customer by customer id \"([^\"]*)\" _Customer_$")
	public void a_user_queries_the_customer_by_customer_id__Customer_(String customerId) throws Throwable {
		try {
			customer = retrieveOne(Customer.class, "/customer/" + customerId);
		} catch (Exception e) {
			customer = null;
		}
	}

	@Then("^the customer with name \"([^\"]*)\" and phone number \"([^\"]*)\" should be returned _Customer_$")
	public void the_customer_with_name_and_phone_number_should_be_returned__Customer_(
			String customerId, String customerPhone) throws Throwable {
		assertTrue(customer.getAssetId().equalsIgnoreCase(customerId));
		assertTrue(customer.getPhone().equalsIgnoreCase(customerPhone));
	}

	@When("^a user updates the customer with name \"([^\"]*)\" and phone number \"([^\"]*)\" _Customer_$")
	public void a_user_update_the_customer_with_name_and_phone_number__Customer_(
			String customerId, String customerPhone) throws Throwable {
		String url = "/customer/" + customerId;
		customer.setName(customerId);
		customer.setPhone(customerPhone);
		customer.setUri("/customer/" + customerId);
        List<Customer> list = new ArrayList<>();
        list.add(customer);
		put(list, url);
	}

	@When("^a user deletes the customer \"([^\"]*)\" _Customer_$")
	public void a_user_deletes_the_customer__Customer_(String customerId) throws Throwable {
		delete("/customer/" + customerId);
	}

	@Then("^the customer \"([^\"]*)\" is not exist _Customer_$")
	public void the_customer_is_not_exist__Customer_(String customerId) throws Throwable {
		assertTrue(customer == null);
	}

}
