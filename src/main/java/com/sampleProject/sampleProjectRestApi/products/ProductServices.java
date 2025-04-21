package com.sampleProject.sampleProjectRestApi.products;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServices {
    private static List<Product> products = new ArrayList<>(3);
    static {
//        products.add(new Products(1,"airpods","Apple airpod 2015",20.0,(new Category(1,"44","hj")),"jjkjkjkw"));
//        products.add(new Products(2,"Nike Airmax 2015","Nike 2015 All new Shoes For Men",100.00,"Men Shoes",""));
//        products.add(new Products(3,"Iphone 15","new Iphone uses apple intelligence",1500,"Iphone",""));
    }
    public List<Product> showAllProducts(){
        return products;
    }
}
