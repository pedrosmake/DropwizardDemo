package org.kainos.ea.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.kainos.ea.exceptions.DoesNotExistException;
import org.kainos.ea.exceptions.FailedToCreateException;
import org.kainos.ea.exceptions.InvalidException;
import org.kainos.ea.models.Order;
import org.kainos.ea.models.OrderRequest;
import org.kainos.ea.models.UserRole;
import org.kainos.ea.services.OrderService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Orders API")
@Path("/api/orders")
public class OrderController {
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(value = "Return all orders", authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION), response = Order.class)
    public Response getOrders() {
        try {
            return Response.ok().entity(orderService.getAllOrders()).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(value = "Return an Order", authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION), response = Order.class)
    public Response getOrderById(@PathParam("orderId") int orderId) {
        try {
            return Response.ok().entity(orderService.getOrderById(orderId))
                    .build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN})
    @ApiOperation(value = "Create an Order", authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION), response = Order.class)
    public Response createOrder(OrderRequest orderRequest) {
        try {
            return Response.status(Response.Status.CREATED)
                    .entity(orderService.createOrder(orderRequest)).build();
        } catch (FailedToCreateException | SQLException e) {
            return Response.serverError().build();
        } catch (InvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN})
    @ApiOperation(value = "Update an Order", authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION), response = Order.class)
    public Response updateOrder(@PathParam("orderId") int orderId,
                                OrderRequest orderRequest) {
        try {
            orderService.updateOrder(orderId, orderRequest);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (InvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN})
    @ApiOperation(value = "Delete an Order", authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION), response = Order.class)
    public Response deleteOrder(@PathParam("orderId") int orderId) {
        try {
            orderService.deleteOrder(orderId);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }
}
