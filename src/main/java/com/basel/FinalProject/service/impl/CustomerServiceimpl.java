package com.basel.FinalProject.service.impl;

import com.basel.FinalProject.enitity.Customer;
import com.basel.FinalProject.enitity.Order;
import com.basel.FinalProject.payload.CustomerDto;
import com.basel.FinalProject.payload.OrderDTO;
import com.basel.FinalProject.repository.CustomerRepository;
import com.basel.FinalProject.repository.OrderRepository;
import com.basel.FinalProject.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceimpl  implements CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;



    public CustomerServiceimpl(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstname(customerDto.getFirstName());
        customer.setLastname(customerDto.getLastname());
        customer.setBornAt(customerDto.getBornAt());
        // Set the orders by mapping the order IDs from the DTO to actual Order entities
        for (Long orderid : customerDto.getOrders()) {
            Optional<Order> orderOptional = orderRepository.findById(orderid);
            orderOptional.ifPresent(customer.getOrders()::add);
        }
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDto(savedCustomer);
    }

    @Override
    public List<CustomerDto> GetAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return convertToDtoList(customers);
    }

    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return convertToDto(customer);
        }
        throw new RuntimeException("Customer not found with id: " + id);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setFirstname(customerDto.getFirstName());
            customer.setLastname(customerDto.getLastname());
            customer.setBornAt(customerDto.getBornAt());
            // Set the orders by mapping the order IDs from the DTO to actual Order entities
            customer.getOrders().clear();
            for (Long orderdid : customerDto.getOrders()) {
                Optional<Order> orderOptional = orderRepository.findById(orderdid);
                orderOptional.ifPresent(customer.getOrders()::add);
            }
            Customer updatedCustomer = customerRepository.save(customer);
            return convertToDto(updatedCustomer);
        }
        throw new RuntimeException("Customer not found with id: " + id);
    }

    @Override
    public void deleteCustomerByid(long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customerRepository.delete(customer);
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }







    private CustomerDto convertToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstname());
        customerDto.setLastname(customer.getLastname());
        customerDto.setBornAt(customer.getBornAt());
        // Set the order IDs in the DTO by mapping the Order entities to their IDs
        for (Order order : customer.getOrders()) {
            customerDto.getOrders().add(order.getOrderId());
        }
        return customerDto;
    }

    private List<CustomerDto> convertToDtoList(List<Customer> customers) {
        return customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
