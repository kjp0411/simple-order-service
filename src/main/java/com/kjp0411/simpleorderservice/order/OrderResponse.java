package com.kjp0411.simpleorderservice.order;

public class OrderResponse {

    private Long orderId;
    private String productName;
    private int quantity;

    public OrderResponse(Long orderId, String productName, int quantity) {
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getProduct().getName(),
            order.getQuantity()
        );
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}
