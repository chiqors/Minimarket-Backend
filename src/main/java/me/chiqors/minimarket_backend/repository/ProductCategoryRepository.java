package me.chiqors.minimarket_backend.repository;

import me.chiqors.minimarket_backend.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Page<ProductCategory> findByNameContainingIgnoreCaseOrderByCreatedAtDesc(String name, Pageable pageable);

    Page<ProductCategory> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<ProductCategory> findAllByOrderByCreatedAtDesc();

    ProductCategory findBySlug(String slug);

    ProductCategory findByName(String name);

    ProductCategory findBySkuCreated(String skuCreated);

    boolean existsBySkuCreated(String skuCreated);
}
