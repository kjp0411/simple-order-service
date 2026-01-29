package com.kjp0411.simpleorderservice.order;

import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문 생성
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid OrderCreateRequest request) {
        Long orderId = orderService.createOrder(
            request.getProductId(),
            request.getQuantity()
        );
        return ResponseEntity
            .created(URI.create("/api/orders/" + orderId))
            .build();
    }

    // 주문 단건 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> get(@PathVariable Long orderId) {
        OrderResponse response = orderService.get(orderId);
        return ResponseEntity.ok(response);
    }
}
