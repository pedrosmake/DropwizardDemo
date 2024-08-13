package org.kainos.ea.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.kainos.ea.exceptions.DoesNotExistException;
import org.kainos.ea.exceptions.FailedToCreateException;
import org.kainos.ea.exceptions.InvalidException;
import org.kainos.ea.models.Product;
import org.kainos.ea.models.ProductRequest;
import org.kainos.ea.models.UserRole;
import org.kainos.ea.services.ProductService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Product API")
@Path("/api/products")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(
            value = "Return all products",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = Product.class
    )
    public Response getProducts() {
        try {
            return Response.ok().entity(productService.getAllOrders()).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(
            value = "Return a product",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = Product.class
    )
    public Response getProductById(@PathParam("productId") int productId) {
        try {
            return Response.ok().entity(productService.getProductById(productId)).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN})
    @ApiOperation(
            value = "Create a product",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = Product.class
    )
    public Response createProduct(ProductRequest productRequest) {
        try {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(productService.createProduct(productRequest))
                    .build();
        } catch (FailedToCreateException | SQLException e) {
            return Response.serverError().build();
        } catch (InvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN})
    @ApiOperation(
            value = "Update a product",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = Product.class
    )
    public Response updateProduct(@PathParam("productId") int productId, ProductRequest productRequest) {
        try {
            productService.updateProduct(productId, productRequest);
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
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN})
    @ApiOperation(
            value = "Delete a product",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = Product.class
    )
    public Response deleteProduct(@PathParam("productId") int productId) {
        try {
            productService.deleteProduct(productId);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
