package com.basel.FinalProject.controller;

import com.basel.FinalProject.payload.CustomerDto;
import com.basel.FinalProject.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(value = "CRUD Rest APIs for Post resources")

@RestController
@RequestMapping()
public class CustomerController {


    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Create Customer REST API, new changes")
   // @PreAuthorize("hasRole('Admin_Role')")
    @PostMapping("/api/v1/customers")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {

        Link selflink = linkTo(CustomerController.class).slash(customerDto.getBornAt()).withSelfRel();
        //customerDto.setBornAt(selflink);
        return new ResponseEntity<>(customerService.createCustomer(customerDto), HttpStatus.CREATED);


    }
    @ApiOperation(value = "Get Customer By Id REST API")
    @GetMapping("/api/v1/customers/{id}")
    public ResponseEntity<CustomerDto> getCustomerByIdV1(@PathVariable(name = "id") long id) {





        return ResponseEntity.ok(customerService.getCustomerById(id));
    }
    @ApiOperation(value = "Update Customer By Id REST API")
  //  @PreAuthorize("hasRole('Admin_Role')")
    // update post by id rest api
    @PutMapping("/api/v1/customers/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto, @PathVariable(name = "id") long id) {

        CustomerDto customerDto1 =customerService.updateCustomer(customerDto, id);

        return new ResponseEntity<>(customerDto1, HttpStatus.OK);
    }


    @ApiOperation(value = "Delete Customer By Id REST API")
   // @PreAuthorize("hasRole('Admin_Role')")
    // delete post rest api
    @DeleteMapping("/api/v1/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(name = "id") long id) {

        customerService.deleteCustomerByid(id);

        return new ResponseEntity<>("Customer entity deleted successfully.", HttpStatus.OK);
    }
    @ApiOperation(value = "Get Customer By Id REST API")
    @GetMapping("/api/v1/customers")
    public ResponseEntity<List<CustomerDto>>getAllCustomer(){
       List<CustomerDto> customerDtos= customerService.GetAllCustomer();

       return new ResponseEntity<>(customerDtos,HttpStatus.I_AM_A_TEAPOT);
    }
}
