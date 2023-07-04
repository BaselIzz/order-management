package com.basel.FinalProject.repository;

import com.basel.FinalProject.enitity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {
}
