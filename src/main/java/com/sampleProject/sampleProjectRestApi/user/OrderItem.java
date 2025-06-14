package com.sampleProject.sampleProjectRestApi.user;

import com.sampleProject.sampleProjectRestApi.products.Product;
import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;
    private int quantity;
}
