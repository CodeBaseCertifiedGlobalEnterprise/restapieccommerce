package com.sampleProject.sampleProjectRestApi.products;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sampleProject.sampleProjectRestApi.category.Category;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")  // FIXED: Explicit table name
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference  // prevents infinite loop when serializing
    private Category category;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(length = 500)
    private String description;

    @Column(name = "product_image_url")
    private String productImageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private Integer totalItemSold=0;


    // Constructors
    public Product() {
        this.createdAt = LocalDateTime.now(); // FIXED: Initialize inside constructor
    }

    public Product(String name, Category category, BigDecimal price, String description, String productImageUrl,int totalItemSold) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.productImageUrl = productImageUrl;
        this.totalItemSold=totalItemSold;
        this.createdAt = LocalDateTime.now(); // FIXED: Initialize timestamp
    }

    // Getters and Setters
    public Long getId() { return id; }

    public Integer getTotalItemSold() {
        return totalItemSold;
    }

    public void setTotalItemSold(Integer totalItemSold) {
        this.totalItemSold = totalItemSold;
    }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getProductImageUrl() { return productImageUrl; }
    public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
