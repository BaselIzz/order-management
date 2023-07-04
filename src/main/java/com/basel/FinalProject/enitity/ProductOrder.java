package com.basel.FinalProject.enitity;

import com.basel.FinalProject.enitity.Order;
import com.basel.FinalProject.enitity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderDetailsNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_Id")
    Order orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_Id")
    Product product;

    int quantity;

    double price;

    double vat;
}