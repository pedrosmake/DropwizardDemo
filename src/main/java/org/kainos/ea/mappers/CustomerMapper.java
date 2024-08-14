package org.kainos.ea.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.kainos.ea.models.Customer;
import org.kainos.ea.models.CustomerResponse;

public class CustomerMapper {
	public static List<CustomerResponse> mapCustomerListToCustomerResponseList(List<Customer> customers) {
		return customers.stream()
				.map(customer -> new CustomerResponse(customer.getCustomerId(), customer.getCustomerName()))
				.collect(Collectors.toList());
	}
}
