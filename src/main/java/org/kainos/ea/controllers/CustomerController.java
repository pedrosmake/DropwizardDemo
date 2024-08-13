package org.kainos.ea.controllers;

import io.swagger.annotations.Api;
import org.kainos.ea.services.CustomerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public Response getCustomers() {
        try {
            return Response.ok().entity(customerService.getAllCustomers()).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }
}
