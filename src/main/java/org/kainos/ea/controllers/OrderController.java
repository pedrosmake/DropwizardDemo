package org.kainos.ea.controllers;

import io.swagger.annotations.Api;
import org.kainos.ea.exceptions.DoesNotExistException;
import org.kainos.ea.exceptions.FailedToCreateException;
import org.kainos.ea.exceptions.InvalidException;
import org.kainos.ea.models.OrderRequest;
import org.kainos.ea.services.OrderService;

import javax.ws.rs.*;
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
    public Response getOrderById(@PathParam("orderId") int orderId) {
        try {
            return Response.ok().entity(orderService.getOrderById(orderId)).build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(OrderRequest orderRequest) {
        try {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(orderService.createOrder(orderRequest))
                    .build();
        } catch (FailedToCreateException | SQLException e) {
            return Response.serverError().build();
        } catch (InvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("orderId") int orderId, OrderRequest orderRequest) {
        try {
            orderService.updateOrder(orderId, orderRequest);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (InvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrder(@PathParam("orderId") int orderId) {
        try {
            orderService.deleteOrder(orderId);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
