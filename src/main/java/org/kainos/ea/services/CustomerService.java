package org.kainos.ea.services;

import java.sql.SQLException;
import java.util.List;

import org.kainos.ea.daos.CustomerDao;
import org.kainos.ea.mappers.CustomerMapper;
import org.kainos.ea.models.CustomerResponse;

public class CustomerService {
	CustomerDao customerDao;

	public CustomerService(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public List<CustomerResponse> getAllCustomers() throws SQLException {
		return CustomerMapper.mapCustomerListToCustomerResponseList(customerDao.getAllCustomers());
	}
}
