package com.sampleProject.sampleProjectRestApi.Order;

import com.sampleProject.sampleProjectRestApi.Order.DTO.OrderResponseDTO;
import com.sampleProject.sampleProjectRestApi.Order.DTO.PlaceOrderRequest;
import com.sampleProject.sampleProjectRestApi.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest request, Principal principal) {

        System.out.println(request);
        System.out.println("This got printed out!!!!!!!!");
        if (principal == null) {
            return ResponseEntity.status(401).body("Unauthorized: Missing or invalid token");
        }
        String userName = principal.getName(); // Securely extracted from JWT
        Order order = orderService.placeOrder(request, userName);
        // Convert to DTO manually
        User user = order.getUser();
        OrderResponseDTO dto = new OrderResponseDTO(order.getId(), user.getUserName(), user.getEmail(),order.getShippingAddress());

        return ResponseEntity.ok(dto);
    }

}
