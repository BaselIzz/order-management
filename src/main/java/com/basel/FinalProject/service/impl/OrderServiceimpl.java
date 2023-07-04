package com.basel.FinalProject.service.impl;

import com.basel.FinalProject.enitity.Customer;
import com.basel.FinalProject.enitity.Order;
import com.basel.FinalProject.enitity.Product;
import com.basel.FinalProject.enitity.ProductOrder;
import com.basel.FinalProject.payload.OrderDTO;
import com.basel.FinalProject.repository.CustomerRepository;
import com.basel.FinalProject.repository.OrderRepository;
import com.basel.FinalProject.repository.ProductRepository;
import com.basel.FinalProject.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class OrderServiceimpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderServiceimpl(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderDate(orderDTO.getOrderDate());
        // Set the customer by mapping the customer ID from the DTO to an actual Customer entity
        Optional<Customer> customerOptional = customerRepository.findById(orderDTO.getCustomerId());
        customerOptional.ifPresent(order::setCustomer);
        ProductOrder productOrder= new ProductOrder();

        productOrder.setOrders(order);
        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            return convertToDto(order);
        }
        throw new RuntimeException("Order not found with id: " + id);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return convertToDtoList(orders);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO, Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setOrderDate(orderDTO.getOrderDate());
            // Set the customer by mapping the customer ID from the DTO to an actual Customer entity
            Optional<Customer> customerOptional = customerRepository.findById(orderDTO.getCustomerId());
            customerOptional.ifPresent(order::setCustomer);
            Order updatedOrder = orderRepository.save(order);
            return convertToDto(updatedOrder);
        }
        throw new RuntimeException("Order not found with id: " + id);
    }

    @Override
    public void deleteOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            orderRepository.delete(order);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    private OrderDTO convertToDto(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getOrderId());
        orderDTO.setOrderDate(order.getOrderDate());
        // Set the customer ID in the DTO
        orderDTO.setCustomerId(order.getCustomer().getId());
        return orderDTO;
    }


    private List<OrderDTO> convertToDtoList(List<Order> orders) {
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
//    private List<Order> convertToEntitiyList(List<OrderDTO> orders) {
//        return orders.stream()
//                .map(this::co)
//                .collect(Collectors.toList());
//    }
}

