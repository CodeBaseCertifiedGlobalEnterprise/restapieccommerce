package com.sampleProject.sampleProjectRestApi.category;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(path = "/getcategory")
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }
}
