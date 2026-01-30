package com.kjp0411.simpleorderservice.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 기본 페이징 조회(N+1 문제 발생 가능)
    Page<Order> findAll(Pageable pageable);

    // Fetch join 사용(N+1 문제 해결)
    @Query(
        value = "select o from Order o join fetch o.product",
        countQuery = "select count(o) from Order o"
    )
    Page<Order> findAllWithProduct(Pageable pageable);
}