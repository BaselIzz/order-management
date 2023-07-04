package com.basel.FinalProject.controller;

import com.basel.FinalProject.payload.StockDTo;
import com.basel.FinalProject.service.StockService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }
    @ApiOperation(value = "createStock  REST API")

    @PostMapping
    public ResponseEntity<StockDTo> createStock(@RequestBody StockDTo stockDTO) {
        StockDTo createdStock = stockService.createStock(stockDTO);
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }
    @ApiOperation(value = "getAllStocks  REST API")

    @GetMapping
    public ResponseEntity<List<StockDTo>> getAllStocks() {
        List<StockDTo> stocks = stockService.getAllStocks();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }
    @ApiOperation(value = "getStock By ID REST API")

    @GetMapping("/{id}")
    public ResponseEntity<StockDTo> getStockById(@PathVariable("id") Long id) {
        StockDTo stock = stockService.getStockById(id);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }
    @ApiOperation(value = "update Stock By ID REST API")

    @PutMapping("/{id}")
    public ResponseEntity<StockDTo> updateStock(
            @RequestBody StockDTo stockDTO, @PathVariable("id") Long id) {
        StockDTo updatedStock = stockService.updateStock(stockDTO, id);
        return new ResponseEntity<>(updatedStock, HttpStatus.OK);
    }
    @ApiOperation(value = "Delete Stock By ID REST API")

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStockById(@PathVariable("id") Long id) {
        stockService.deleteStockById(id);
        return ResponseEntity.ok("Stock Deleted successfully.");
    }
}
