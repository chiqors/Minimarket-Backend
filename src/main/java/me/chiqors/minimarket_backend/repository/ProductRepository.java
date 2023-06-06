package me.chiqors.minimarket_backend.repository;

import me.chiqors.minimarket_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySkuCode(String skuCode);
}
