package com.kjp0411.simpleorderservice.product;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id and p.deleted = false")
    Optional<Product> findByIdWithLock(@Param("id") Long id);

    Optional<Product> findByIdAndDeletedFalse(Long id);

    List<Product> findAllByDeletedFalse();
}
