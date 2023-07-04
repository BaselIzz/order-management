package com.basel.FinalProject.enitity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @Column(name = "product_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId;

    String slug;

    String productName;

    String  reference;

    double price;
    double vat;
    boolean stockable;

    @ManyToOne
    @JoinColumn(name = "order_Id")
    ProductOrder productOrder;
//this make me a erorr the server stop working ?
//    @ManyToOne
//    @JoinColumn(name="product_Id")
//    Stock stock;




}