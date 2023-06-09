package me.chiqors.minimarket_backend.repository;

import me.chiqors.minimarket_backend.model.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {
    // Find the most purchased product
    @Query(value = "SELECT p.sku_code, p.name, pc.name AS category, SUM(td.quantity) AS total_purchased FROM transaction_details td JOIN products p ON td.product_id = p.id JOIN product_categories pc ON p.product_category_id = pc.id GROUP BY p.sku_code, p.name, pc.name ORDER BY total_purchased DESC LIMIT 3", nativeQuery = true)
    List<Object[]> findMostPurchasedProduct();

    // Find product often purchased with the product with skuCode
    @Query(value = "SELECT P.sku_code, P.name, pc.NAME AS category, COUNT(td.id) AS total_purchased FROM transaction_details td JOIN products P ON td.product_id = P.id JOIN product_categories pc ON P.product_category_id = pc.ID WHERE td.transaction_id IN ( SELECT td.transaction_id FROM transaction_details td JOIN products P ON td.product_id = P.id WHERE P.sku_code = :skuCode ) AND P.sku_code != :skuCode GROUP BY P.sku_code, P.name, pc.NAME ORDER BY total_purchased DESC LIMIT 3", nativeQuery = true)
    List<Object[]> findProductOftenPurchased(@Param("skuCode") String skuCode);
}
