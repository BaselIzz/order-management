package com.basel.FinalProject.service.impl;

import com.basel.FinalProject.enitity.Product;
import com.basel.FinalProject.enitity.ProductOrder;
import com.basel.FinalProject.payload.ProductDTo;
import com.basel.FinalProject.repository.ProductRepository;
import com.basel.FinalProject.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @Override
    public ProductDTo createProduct(ProductDTo productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Override
    public ProductDTo getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return convertToDTO(product);
        }
        throw new RuntimeException("Product not found with id: " + id);
    }

    @Override
    public List<ProductDTo> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    @Override
    public ProductDTo updateProduct(ProductDTo productDTo, Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setSlug(productDTo.getSlug());
            product.setProductName(productDTo.getProductName());
            product.setReference(productDTo.getReference());
            product.setPrice(productDTo.getPrice());
            product.setVat(productDTo.getVat());
            product.setStockable(productDTo.isStockable());
            Product updatedProduct = productRepository.save(product);
            return convertToDTO(updatedProduct);
        }
        throw new RuntimeException("Product not found with id: " + id);
    }

    @Override
    public void deleteProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productRepository.delete(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    private ProductDTo convertToDTO(Product product) {
        ProductDTo productDTO = new ProductDTo();
        productDTO.setProductId(product.getProductId());
        productDTO.setSlug(product.getSlug());
        productDTO.setProductName(product.getProductName());
        productDTO.setReference(product.getReference());
        productDTO.setPrice(product.getPrice());
        productDTO.setVat(product.getVat());
        productDTO.setStockable(product.isStockable());
        productDTO.setOrderId(product.getProductOrder() != null ? product.getProductOrder().getOrderDetailsNo() : null);
        return productDTO;
    }

    private Product convertToEntity(ProductDTo productDTO) {
        Product product = new Product();
        //product.setProductId(productDTO.getProductId());
        product.setSlug(productDTO.getSlug());
        product.setProductName(productDTO.getProductName());
        product.setReference(productDTO.getReference());
        product.setPrice(productDTO.getPrice());
        product.setVat(productDTO.getVat());
        product.setStockable(productDTO.isStockable());
        // Set the ProductOrder based on the orderId in the DTO
        if (productDTO.getOrderId() != null) {
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrderDetailsNo(productDTO.getOrderId());
            product.setProductOrder(productOrder);
        }
        return product;
    }
}
