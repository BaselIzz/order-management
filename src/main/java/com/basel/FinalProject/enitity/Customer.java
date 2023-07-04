package com.basel.FinalProject.enitity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Table(name = "customers")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column

    private String Firstname;
    @Column

    private  String lastname;
    @Column

    private LocalDate bornAt;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_Id")
    @JoinTable(name = "customer_order", joinColumns = {@JoinColumn(name = "customer_Id")}, inverseJoinColumns = {@JoinColumn(name = "order_Id")})
    Set<Order> orders = new HashSet<Order>(0);

}
