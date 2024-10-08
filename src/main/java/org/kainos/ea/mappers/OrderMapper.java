package org.kainos.ea.mappers;

import org.kainos.ea.models.Order;
import org.kainos.ea.models.OrderResponse;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static List<OrderResponse> mapOrderListToOrderResponseList(
            List<Order> orders) {
        return orders.stream()
                .map(order -> new OrderResponse(order.getOrderId(),
                        order.getOrderDate()))
                .collect(Collectors.toList());
    }
}
