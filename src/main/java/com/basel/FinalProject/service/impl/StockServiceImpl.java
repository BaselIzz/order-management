package com.basel.FinalProject.service.impl;


import com.basel.FinalProject.enitity.Product;
import com.basel.FinalProject.enitity.Stock;
import com.basel.FinalProject.payload.StockDTo;
import com.basel.FinalProject.repository.ProductRepository;
import com.basel.FinalProject.repository.StockRepository;
import com.basel.FinalProject.service.StockService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public StockServiceImpl(StockRepository stockRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }



    @Override
    public StockDTo createStock(StockDTo stockDTO) {
        Stock stock = new Stock();
        stock.setQuantity(stockDTO.getQuantity());
        stock.setUpdatedAt(LocalDate.now());

        Optional<Product> productOptional = productRepository.findById(stockDTO.getProductId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            stock.setProduct(product);
        } else {
            throw new RuntimeException("Product not found with id: " + stockDTO.getProductId());
        }

        Stock savedStock = stockRepository.save(stock);
        return convertToDto(savedStock);
    }

    @Override
    public List<StockDTo> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return convertToDtoList(stocks);
    }

    @Override
    public StockDTo getStockById(Long id) {
        Optional<Stock> stockOptional = stockRepository.findById(id);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            return convertToDto(stock);
        }
        throw new RuntimeException("Stock not found with id: " + id);
    }


    @Override
    public StockDTo updateStock(StockDTo stockDTO, Long id) {
        Optional<Stock> stockOptional = stockRepository.findById(id);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            stock.setQuantity(stockDTO.getQuantity());
            stock.setUpdatedAt(LocalDate.now());

            Optional<Product> productOptional = productRepository.findById(stockDTO.getProductId());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                stock.setProduct(product);
            } else {
                throw new RuntimeException("Product not found with id: " + stockDTO.getProductId());
            }

            Stock updatedStock = stockRepository.save(stock);
            return convertToDto(updatedStock);
        }
        throw new RuntimeException("Stock not found with id: " + id);
    }

    @Override
    public void deleteStockById(Long id) {
        Optional<Stock> stockOptional = stockRepository.findById(id);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            stockRepository.delete(stock);
        } else {
            throw new RuntimeException("Stock not found with id: " + id);
        }
    }

    private StockDTo convertToDto(Stock stock) {
        StockDTo stockDTO = new StockDTo();
        stockDTO.setId(stock.getId());
        stockDTO.setProductId(stock.getProduct().getProductId());
        stockDTO.setQuantity(stock.getQuantity());
        stockDTO.setUpdatedAt(stock.getUpdatedAt());
        return stockDTO;
    }

    private List<StockDTo> convertToDtoList(List<Stock> stocks) {
        return stocks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
