package me.chiqors.minimarket_backend.repository;

import me.chiqors.minimarket_backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCaseAndIsDeletedIsFalseOrderByCreatedAtDesc(String name, Pageable pageable);
    Page<Product> findByIsDeletedIsFalseOrderByCreatedAtDesc(Pageable pageable);
    Product findBySkuCodeAndIsDeletedIsFalse(String skuCode);
    Product findBySlugAndIsDeletedIsFalse(String slug);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);
}
