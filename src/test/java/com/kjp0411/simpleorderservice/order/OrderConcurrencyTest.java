package com.kjp0411.simpleorderservice.order;

import com.kjp0411.simpleorderservice.product.Product;
import com.kjp0411.simpleorderservice.product.ProductRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class OrderConcurrencyTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    private Long productId;

    @BeforeEach
    void setUp() {
        // 테스트 전 상품 초기화
        Product product = new Product("동시성 테스트 상품", 1000, 1);
        productRepository.save(product);
        productId = product.getId();
    }

    @Test
    void 동시에_주문하면_재고_정합성이_깨질_수_있다() throws InterruptedException {
        // Given
        int threadCount = 2;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    orderService.createOrder(productId, 1);
                } catch (Exception e) {
                    System.out.println("주문 실패: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        Product result = productRepository.findById(productId).orElseThrow();
        System.out.println("남은 재고: " + result.getStock());
    }
}
