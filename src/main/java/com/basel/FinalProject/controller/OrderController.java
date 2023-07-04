package com.basel.FinalProject.controller;

import com.basel.FinalProject.payload.OrderDTO;
import com.basel.FinalProject.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(value = "CRUD Rest APIs for Order resources")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "Create Order REST API")
    @PostMapping
    public ResponseEntity<EntityModel<OrderDTO>> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        Link selfLink = linkTo(methodOn(OrderController.class).getOrderById(createdOrder.getId())).withSelfRel();
        EntityModel<OrderDTO> resource = EntityModel.of(createdOrder, selfLink);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get Order By Id REST API")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> getOrderById(@PathVariable("id") Long id) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        Link selfLink = linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel();
        EntityModel<OrderDTO> resource = EntityModel.of(orderDTO, selfLink);
        return ResponseEntity.ok(resource);
    }

    @ApiOperation(value = "Update Order By Id REST API")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> updateOrder(@PathVariable("id") Long id, @Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrder = orderService.updateOrder(orderDTO, id);
        Link selfLink = linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel();
        EntityModel<OrderDTO> resource = EntityModel.of(updatedOrder, selfLink);
        return ResponseEntity.ok(resource);
    }

    @ApiOperation(value = "Delete Order By Id REST API")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.ok("Order deleted successfully.");
    }

    @ApiOperation(value = "Get All Orders REST API")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        List<EntityModel<OrderDTO>> orderResources = orders.stream()
                .map(order -> EntityModel.of(order,
                        linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel(),
                        linkTo(OrderController.class).slash(order.getId()).withRel("update"),
                        linkTo(OrderController.class).slash(order.getId()).withRel("delete")
                ))
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel();
        CollectionModel<EntityModel<OrderDTO>> resources = CollectionModel.of(orderResources, selfLink);
        return ResponseEntity.ok(resources);
    }
}