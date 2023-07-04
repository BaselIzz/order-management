package com.basel.FinalProject.controller;

import com.basel.FinalProject.payload.ProductOrderDTo;
import com.basel.FinalProject.service.ProductOrderService;
import io.swagger.annotations.ApiOperation;
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

@RestController
@RequestMapping("/api/product-orders")
public class ProductOrderController {
    private final ProductOrderService productOrderService;

    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }
    @ApiOperation(value = "Create ProductOrder REST API")

    @PostMapping
    public ResponseEntity<EntityModel<ProductOrderDTo>> createProductOrder(@RequestBody ProductOrderDTo productOrderDTO) {
        ProductOrderDTo createdProductOrder = productOrderService.createProductOrder(productOrderDTO);
        EntityModel<ProductOrderDTo> productOrderEntity = addLinks(createdProductOrder);
        return new ResponseEntity<>(productOrderEntity, HttpStatus.CREATED);
    }
    @ApiOperation(value = "Get All ProductOrder REST API")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductOrderDTo>>> getAllProductOrders() {
        List<ProductOrderDTo> productOrders = productOrderService.getAllProductOrders();
        List<EntityModel<ProductOrderDTo>> productOrderEntities = productOrders.stream()
                .map(this::addLinks)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ProductOrderDTo>> collectionModel = CollectionModel.of(productOrderEntities);
        addSelfLink(collectionModel);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
    @ApiOperation(value = "Get ProductOrder By ID REST API")

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductOrderDTo>> getProductOrderById(@PathVariable("id") Long id) {
        ProductOrderDTo productOrder = productOrderService.getProductOrderById(id);
        EntityModel<ProductOrderDTo> productOrderEntity = addLinks(productOrder);
        return new ResponseEntity<>(productOrderEntity, HttpStatus.OK);
    }
    @ApiOperation(value = "Update ProductOrder By ID REST API")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductOrderDTo>> updateProductOrder(
            @RequestBody ProductOrderDTo productOrderDTO, @PathVariable("id") Long id) {
        ProductOrderDTo updatedProductOrder = productOrderService.updateProductOrder(productOrderDTO, id);
        EntityModel<ProductOrderDTo> updatedProductOrderEntity = addLinks(updatedProductOrder);
        return new ResponseEntity<>(updatedProductOrderEntity, HttpStatus.OK);
    }
    @ApiOperation(value = "Delete ProductOrder By ID REST API")

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductOrderById(@PathVariable("id") Long id) {
        productOrderService.deleteProductOrderById(id);
        return ResponseEntity.ok("Order ProductOrder successfully.");
    }

    private EntityModel<ProductOrderDTo> addLinks(ProductOrderDTo productOrder) {
        EntityModel<ProductOrderDTo> productOrderEntity = EntityModel.of(productOrder);
        Link selfLink = linkTo(
                        methodOn(ProductOrderController.class).getProductOrderById(productOrder.getId()))
                .withSelfRel();
        productOrderEntity.add(selfLink);
        return productOrderEntity;
    }

    private void addSelfLink(CollectionModel<EntityModel<ProductOrderDTo>> collectionModel) {
        Link selfLink = linkTo(ProductOrderController.class).withSelfRel();
        collectionModel.add(selfLink);
    }
}
