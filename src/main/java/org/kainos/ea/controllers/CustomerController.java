package org.kainos.ea.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.kainos.ea.models.Customer;
import org.kainos.ea.models.UserRole;
import org.kainos.ea.services.CustomerService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Customer API")
@Path("/api/customers")
public class CustomerController {
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(value = "Return all customers", authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION), response = Customer.class)
    public Response getCustomers() {
        try {
            return Response.ok().entity(customerService.getAllCustomers())
                    .build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }
}
