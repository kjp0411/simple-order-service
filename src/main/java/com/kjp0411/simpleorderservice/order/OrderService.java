package com.kjp0411.simpleorderservice.order;

import com.kjp0411.simpleorderservice.product.Product;
import com.kjp0411.simpleorderservice.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * 주문 생성 유스케이스
     * - 상품 조회 → 재고 차감 → 주문 생성/저장까지 하나의 트랜잭션으로 처리
     * - 중간 실패 시 전체 롤백 보장
     */
    @Transactional
    public Long createOrder(Long productId, int quantity) {
        // 1. 상품 조회
        Product product = productRepository.findById(productId)
            .orElseThrow(()-> new IllegalArgumentException("상품이 존재하지 않습니다."));

        // 2. 재고 차감
        product.decreaseStock(quantity);

        // 3. 주문 생성
        Order order = new Order(product, quantity);

        // 4. 주문 저장
        Order savedOrder = orderRepository.save(order);

        // 5. 결과 반환(주문 ID)
        return savedOrder.getId();
    }

    @Transactional(readOnly = true)
    public OrderResponse get(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
        return OrderResponse.from(order);
    }
}