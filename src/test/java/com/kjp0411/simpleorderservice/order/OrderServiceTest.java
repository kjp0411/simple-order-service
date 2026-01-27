package com.kjp0411.simpleorderservice.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kjp0411.simpleorderservice.product.Product;
import com.kjp0411.simpleorderservice.product.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    void createOrder() {
        // given
        Long productId = 1L;
        int quantity = 2;

        Product product = new Product("테스트 상품", 1000, 10);

        when(productRepository.findById(productId))
            .thenReturn(Optional.of(product));

        when(orderRepository.save(any(Order.class)))
            .thenAnswer(invocation -> {
                Order savedOrder = invocation.getArgument(0);
                ReflectionTestUtils.setField(savedOrder, "id", 1L);
                return savedOrder;
            });

        // when
        Long orderId = orderService.createOrder(productId, quantity);

        // then
        assertNotNull(orderId);
        assertEquals(8, product.getStock());
        verify(orderRepository).save(any(Order.class));
    }
}