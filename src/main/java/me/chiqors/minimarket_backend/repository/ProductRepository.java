package me.chiqors.minimarket_backend.repository;

import me.chiqors.minimarket_backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCaseAndIsDeletedIsFalseOrderByCreatedAtDesc(String name, Pageable pageable);
    Page<Product> findByIsDeletedIsFalseOrderByCreatedAtDesc(Pageable pageable);
    Product findBySkuCodeAndIsDeletedIsFalse(String skuCode);
    Product findBySlugAndIsDeletedIsFalse(String slug);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    Product findBySkuCode(String skuCode);

    // get 3 top most bought products from transaction detail
    @Query(value = "SELECT p.* FROM products p INNER JOIN transaction_details td ON p.id = td.product_id GROUP BY p.id ORDER BY SUM(td.quantity) DESC LIMIT 3", nativeQuery = true)
    List<Product> findTop3MostBoughtProducts();
}
