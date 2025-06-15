package com.sampleProject.sampleProjectRestApi.Order.DTO;

public class OrderResponseDTO {
    private Long orderId;
    private String userName;
    private String email;
    private String address;

    public OrderResponseDTO(Long orderId, String userName, String email, String address) {
        this.orderId = orderId;
        this.userName = userName;
        this.email = email;
        this.address = address;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
