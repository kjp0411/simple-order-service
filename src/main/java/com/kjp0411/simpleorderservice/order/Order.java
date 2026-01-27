package com.kjp0411.simpleorderservice.order;

import com.kjp0411.simpleorderservice.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문은 하나의 상품만 참조 (단일 상품 주문)
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int quantity;
    private LocalDateTime createdAt;

    protected Order() {
    }

    public Order(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
    }
}