package me.chiqors.minimarket_backend.repository;

import me.chiqors.minimarket_backend.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Page<ProductCategory> findByNameContainingIgnoreCaseOrderByCreatedAtDesc(String name, Pageable pageable);

    Page<ProductCategory> findAllByOrderByCreatedAtDesc(Pageable pageable);

    ProductCategory findBySlug(String slug);
}
