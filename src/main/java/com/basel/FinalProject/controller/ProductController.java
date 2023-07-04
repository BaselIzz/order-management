package com.basel.FinalProject.controller;


import com.basel.FinalProject.payload.ProductDTo;
import com.basel.FinalProject.service.ProductService;
import io.swagger.annotations.Api;
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

@Api(value = "CRUD Rest APIs for Product resources")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "Create Product REST API")
    @PostMapping
    public ResponseEntity<ProductDTo> createProduct(@RequestBody ProductDTo productDTO) {
        ProductDTo createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get Product By ID REST API")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTo> getProductById(@PathVariable Long id) {
        ProductDTo productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @ApiOperation(value = "Get All Products REST API")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductDTo>>> getAllProducts() {
        List<ProductDTo> products = productService.getAllProducts();

        List<EntityModel<ProductDTo>>  Productstream = products.stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(OrderController.class).getOrderById(product.getProductId())).withSelfRel(),
                        linkTo(OrderController.class).slash(product.getProductId()).withRel("update"),
                        linkTo(OrderController.class).slash(product.getProductId()).withRel("delete")
                ))
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel();
        CollectionModel<EntityModel<ProductDTo>> resources = CollectionModel.of(Productstream, selfLink);
        return ResponseEntity.ok(resources);
    }

    @ApiOperation(value = "Update Product By ID REST API")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTo> updateProduct(@RequestBody ProductDTo productDTO, @PathVariable Long id) {
        ProductDTo updatedProduct = productService.updateProduct(productDTO, id);
        return ResponseEntity.ok(updatedProduct);
    }

    @ApiOperation(value = "Delete Product By ID REST API")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}
