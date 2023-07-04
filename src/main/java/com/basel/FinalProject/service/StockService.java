package com.basel.FinalProject.service;


import com.basel.FinalProject.payload.StockDTo;

import java.util.List;

public interface StockService {
    StockDTo createStock(StockDTo stockDTO);
    List<StockDTo> getAllStocks();
    StockDTo getStockById(Long id);
    StockDTo updateStock(StockDTo stockDTO, Long id);
    void deleteStockById(Long id);
}
