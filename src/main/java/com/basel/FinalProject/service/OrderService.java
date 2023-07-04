package com.basel.FinalProject.service;

import com.basel.FinalProject.payload.OrderDTO;

import java.util.List;
public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDto);
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(Long orderId);
    OrderDTO updateOrder(OrderDTO orderDto, Long orderId);
    void deleteOrderById(Long orderId);
}


