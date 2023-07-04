package com.basel.FinalProject.service;


import com.basel.FinalProject.payload.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;
public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    List<CustomerDto> GetAllCustomer();
    CustomerDto getCustomerById(Long customberid);
    CustomerDto updateCustomer(CustomerDto customerDto,long id);
    void deleteCustomerByid(long id);
}
