package com.kjp0411.simpleorderservice.product;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 등록
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ProductCreateRequest request) {
        Long productId = productService.create(request);
        return ResponseEntity
            .created(URI.create("/api/products/" + productId))
            .build();
    }

    // 상품 수정
    @PutMapping("/{productId}")
    public ResponseEntity<Void> update(@PathVariable Long productId, @RequestBody @Valid ProductUpdateRequest request) {
        productService.update(productId, request);
        return ResponseEntity.ok().build();
    }

    // 상품 단건 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long productId) {
        ProductResponse response = productService.get(productId);
        return ResponseEntity.ok(response);
    }

    // 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}