package com.sampleProject.sampleProjectRestApi.products;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    ProductServices productServices;
    ProductRepository productRepository;

    public ProductController(ProductServices productServices,ProductRepository productRepository) {
        this.productServices = productServices;
       this.productRepository = productRepository;
    }

    @GetMapping(path = "/getproducts", produces = "application/json")
    public List<Product> showProducts(){
        return productRepository.findAll();
    }

    @GetMapping(path = "/getproducts/category/name/{categoryName}", produces = "application/json")
    public List<Product> getProductsByCategoryName(@PathVariable String categoryName){
        return productRepository.findByCategoryName(categoryName);
    }
    @GetMapping(path = "/testapi", produces = "application/json")
    public String testapi(){
        return "This is a test for rest Api";
//        return productRepository.findByCategoryName(categoryName);
    }
    @GetMapping(path = "/testSpringSecurity", produces = "application/json")
    public List<Product> showProduct(){
        return productServices.showAllProducts();
    }
    @GetMapping(path = "/latest")
    public ResponseEntity<List<Product>> getLatestItems(){
        List<Product> latestItems = productRepository.findTop10ByOrderByCreatedAtDesc();
        return ResponseEntity.ok(latestItems);
    }
}
