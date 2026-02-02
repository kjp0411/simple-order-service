package com.kjp0411.simpleorderservice.product;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 등록
    public Long create(ProductCreateRequest request) {
        Product product = new Product(
            request.getName(),
            request.getPrice(),
            request.getStock()
        );

        Product saved = productRepository.save(product);
        return saved.getId();
    }

    // 상품 수정
    public void update(Long productId, ProductUpdateRequest request) {
        Product product = productRepository.findByIdAndDeletedFalse(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        product.update(
            request.getName(),
            request.getPrice(),
            request.getStock()
        );
    }

    // 상품 단건 조회
    @Transactional(readOnly = true)
    public ProductResponse get(Long productId) {
        Product product = productRepository.findByIdAndDeletedFalse(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        return ProductResponse.from(product);
    }

    // 상품 목록 조회
    @Transactional(readOnly = true)
    public List<ProductResponse> getAll() {
        return productRepository.findAllByDeletedFalse().stream()
            .map(ProductResponse::from)
            .toList();
    }

    // 상품 삭제
    public void delete(Long productId) {
        Product product = productRepository.findByIdAndDeletedFalse(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        product.delete();
    }
}
