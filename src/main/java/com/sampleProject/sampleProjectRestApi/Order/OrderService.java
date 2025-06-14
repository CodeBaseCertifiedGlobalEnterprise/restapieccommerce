package com.sampleProject.sampleProjectRestApi.Order;

import com.sampleProject.sampleProjectRestApi.Order.DTO.OrderItemRequest;
import com.sampleProject.sampleProjectRestApi.Order.DTO.PlaceOrderRequest;
import com.sampleProject.sampleProjectRestApi.products.Product;
import com.sampleProject.sampleProjectRestApi.products.ProductRepository;
import com.sampleProject.sampleProjectRestApi.user.User;
import com.sampleProject.sampleProjectRestApi.user.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Order placeOrder(PlaceOrderRequest request, String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus(order.getStatus());
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(Math.toIntExact(itemRequest.getProductId())).orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPriceEach(product.getPrice());
            orderItem.setProductName(product.getName());
            orderItem.setProductImageUrl(product.getProductImageUrl());
            orderItem.setOrder(order); // important: link to parent
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }
}
