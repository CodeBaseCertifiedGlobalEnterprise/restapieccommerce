package com.sampleProject.sampleProjectRestApi.products;

import com.sampleProject.sampleProjectRestApi.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByCategoryName(String categoryName);
    List<Product> findTop10ByOrderByCreatedAtDesc();
}
