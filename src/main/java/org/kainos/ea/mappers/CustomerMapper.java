package org.kainos.ea.mappers;

import org.kainos.ea.models.Customer;
import org.kainos.ea.models.CustomerResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {
    public static List<CustomerResponse> mapCustomerListToCustomerResponseList(
            List<Customer> customers) {
        return customers.stream()
                .map(customer -> new CustomerResponse(customer.getCustomerId(),
                        customer.getCustomerName()))
                .collect(Collectors.toList());
    }
}
