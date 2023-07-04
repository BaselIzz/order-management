package com.basel.FinalProject.repository;

import com.basel.FinalProject.enitity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ProductRepository extends JpaRepository<Product,Long> {
}
