package com.Order.Order.Services;


import com.Order.Order.Dto.OrderDto;

import java.util.List;

public interface OrderServices {
    String addOrder(OrderDto orderDto);
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(long id);
    String updateOrder(long id, OrderDto orderDto);
    String deleteOrder(long id);
}