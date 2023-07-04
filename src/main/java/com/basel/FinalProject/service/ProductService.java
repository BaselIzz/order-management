package com.basel.FinalProject.service;

import com.basel.FinalProject.payload.ProductDTo;

import java.util.List;

public interface ProductService {
    ProductDTo createProduct(ProductDTo productDTO);

    ProductDTo getProductById(Long id);

    List<ProductDTo> getAllProducts();

    ProductDTo updateProduct(ProductDTo productDTO, Long id);

    void deleteProductById(Long id);
}

