package com.basel.FinalProject.enitity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_Id")
    private Customer customer;


    @OneToMany
    private List<ProductOrder> productOrders;

}
