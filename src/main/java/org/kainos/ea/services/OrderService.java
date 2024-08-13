package org.kainos.ea.services;

import org.kainos.ea.daos.OrderDao;
import org.kainos.ea.exceptions.DoesNotExistException;
import org.kainos.ea.exceptions.Entity;
import org.kainos.ea.exceptions.FailedToCreateException;
import org.kainos.ea.exceptions.InvalidException;
import org.kainos.ea.mappers.OrderMapper;
import org.kainos.ea.models.Order;
import org.kainos.ea.models.OrderRequest;
import org.kainos.ea.models.OrderResponse;
import org.kainos.ea.validators.OrderValidator;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    OrderDao orderDao;
    OrderValidator orderValidator;

    public OrderService(OrderDao orderDao, OrderValidator orderValidator) {
        this.orderDao = orderDao;
        this.orderValidator = orderValidator;
    }

    public List<OrderResponse> getAllOrders() throws SQLException {
        return OrderMapper.mapOrderListToOrderResponseList(orderDao.getAllOrders());
    }

    public Order getOrderById(int orderId) throws SQLException, DoesNotExistException {
        Order order = orderDao.getOrderById(orderId);
        if (order == null) {
            throw new DoesNotExistException(Entity.ORDER);
        }

        return order;
    }

    public int createOrder(OrderRequest orderRequest) throws FailedToCreateException, SQLException, InvalidException, DoesNotExistException {
        orderValidator.validateOrder(orderRequest);
        int id = orderDao.createOrder(orderRequest);

        if (id == -1) {
            throw new FailedToCreateException(Entity.ORDER);
        }

        return id;
    }

    public void updateOrder(int id, OrderRequest orderRequest) throws DoesNotExistException, SQLException, InvalidException {
        orderValidator.validateOrder(orderRequest);

        Order orderToUpdate = orderDao.getOrderById(id);

        if (orderToUpdate == null) {
            throw new DoesNotExistException(Entity.ORDER);
        }

        orderDao.updateOrder(id, orderRequest);
    }

    public void deleteOrder(int orderId) throws SQLException, DoesNotExistException {
        Order order = orderDao.getOrderById(orderId);
        if (order == null) {
            throw new DoesNotExistException(Entity.PRODUCT);
        }
        orderDao.deleteOrder(orderId);
    }
}
