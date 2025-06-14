package com.sampleProject.sampleProjectRestApi.products;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServices {
    private static List<Product> products = new ArrayList<>(3);
    public List<Product> showAllProducts(){
        return products;
    }
}
