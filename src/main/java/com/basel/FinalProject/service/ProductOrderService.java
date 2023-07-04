package com.basel.FinalProject.service;

import com.basel.FinalProject.payload.ProductOrderDTo;

import java.util.List;

public interface ProductOrderService {
    ProductOrderDTo createProductOrder(ProductOrderDTo productOrderDTO);

    List<ProductOrderDTo> getAllProductOrders();

    ProductOrderDTo getProductOrderById(Long id);

    ProductOrderDTo updateProductOrder(ProductOrderDTo productOrderDTO, Long id);

    void deleteProductOrderById(Long id);
}
