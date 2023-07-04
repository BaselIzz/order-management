package com.basel.FinalProject.service.impl;

import com.basel.FinalProject.enitity.Order;
import com.basel.FinalProject.enitity.Product;
import com.basel.FinalProject.enitity.ProductOrder;
import com.basel.FinalProject.payload.ProductOrderDTo;
import com.basel.FinalProject.repository.OrderRepository;
import com.basel.FinalProject.repository.ProductOrderRepository;
import com.basel.FinalProject.repository.ProductRepository;
import com.basel.FinalProject.service.ProductOrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ProductOrderServiceImpl implements ProductOrderService {
    private final ProductOrderRepository productOrderRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository, OrderRepository orderRepository,
                                   ProductRepository productRepository) {
        this.productOrderRepository = productOrderRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }



    @Override
    public ProductOrderDTo createProductOrder(ProductOrderDTo productOrderDTO) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setQuantity(productOrderDTO.getQuantity());
        productOrder.setPrice(productOrderDTO.getPrice());
        productOrder.setVat(productOrderDTO.getVat());

        // Set the order by mapping the order ID from the DTO to the actual Order entity
        Optional<Order> orderOptional = orderRepository.findById(productOrderDTO.getOrderId());
        orderOptional.ifPresent(productOrder::setOrders);

        // Set the product by mapping the product ID from the DTO to the actual Product entity
        Optional<Product> productOptional = productRepository.findById(productOrderDTO.getProductId());
        productOptional.ifPresent(productOrder::setProduct);

        ProductOrder savedProductOrder = productOrderRepository.save(productOrder);
        return convertToDto(savedProductOrder);
    }

    @Override
    public List<ProductOrderDTo> getAllProductOrders() {
        List<ProductOrder> productOrders = productOrderRepository.findAll();
        return convertToDtoList(productOrders);
    }

    @Override
    public ProductOrderDTo getProductOrderById(Long id) {
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findById(id);
        if (productOrderOptional.isPresent()) {
            ProductOrder productOrder = productOrderOptional.get();
            return convertToDto(productOrder);
        }
        throw new RuntimeException("Product Order not found with id: " + id);
    }



    @Override
    public ProductOrderDTo updateProductOrder(ProductOrderDTo productOrderDTO, Long id) {
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findById(id);
        if (productOrderOptional.isPresent()) {
            ProductOrder productOrder = productOrderOptional.get();
            productOrder.setQuantity(productOrderDTO.getQuantity());
            productOrder.setPrice(productOrderDTO.getPrice());
            productOrder.setVat(productOrderDTO.getVat());

            // Set the order by mapping the order ID from the DTO to the actual Order entity
            Optional<Order> orderOptional = orderRepository.findById(productOrderDTO.getOrderId());
            orderOptional.ifPresent(productOrder::setOrders);

            // Set the product by mapping the product ID from the DTO to the actual Product entity
            Optional<Product> productOptional = productRepository.findById(productOrderDTO.getProductId());
            productOptional.ifPresent(productOrder::setProduct);

            ProductOrder updatedProductOrder = productOrderRepository.save(productOrder);
            return convertToDto(updatedProductOrder);
        }
        throw new RuntimeException("Product Order not found with id: " + id);
    }

    @Override
    public void deleteProductOrderById(Long id) {
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findById(id);
        if (productOrderOptional.isPresent()) {
            ProductOrder productOrder = productOrderOptional.get();
            productOrderRepository.delete(productOrder);
        } else {
            throw new RuntimeException("Product Order not found with id: " + id);
        }
    }

    private ProductOrderDTo convertToDto(ProductOrder productOrder) {
        ProductOrderDTo productOrderDTO = new ProductOrderDTo();
        productOrderDTO.setId(productOrder.getOrderDetailsNo());
        productOrderDTO.setQuantity(productOrder.getQuantity());
        productOrderDTO.setPrice(productOrder.getPrice());
        productOrderDTO.setVat(productOrder.getVat());
        productOrderDTO.setOrderId(productOrder.getOrders().getOrderId());
        productOrderDTO.setProductId(productOrder.getProduct().getProductId());
        return productOrderDTO;
    }

    private List<ProductOrderDTo> convertToDtoList(List<ProductOrder> productOrders) {
        return productOrders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}