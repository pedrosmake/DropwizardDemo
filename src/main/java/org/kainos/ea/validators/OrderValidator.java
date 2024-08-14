package org.kainos.ea.validators;

import org.kainos.ea.daos.CustomerDao;
import org.kainos.ea.exceptions.DoesNotExistException;
import org.kainos.ea.exceptions.Entity;
import org.kainos.ea.exceptions.InvalidException;
import org.kainos.ea.models.Customer;
import org.kainos.ea.models.OrderRequest;

import java.sql.SQLException;

public class OrderValidator {
    public void validateOrder(OrderRequest orderRequest)
            throws InvalidException, DoesNotExistException {
        CustomerDao customerDao = new CustomerDao();
        try {
            Customer cust =
                    customerDao.getCustomerById(orderRequest.getCustomerId());
            if (cust == null) {
                throw new DoesNotExistException(Entity.CUSTOMER);
            }
        } catch (SQLException e) {
            throw new InvalidException(Entity.CUSTOMER,
                    "Can't get customer details");
        }

    }
}
