package com.sampleProject.sampleProjectRestApi.Order.DTO;

import java.util.List;

public class PlaceOrderRequest {
    private List<OrderItemRequest> items;
    private String shippingAddress;

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
