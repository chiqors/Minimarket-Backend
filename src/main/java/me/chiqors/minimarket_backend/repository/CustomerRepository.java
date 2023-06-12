package me.chiqors.minimarket_backend.repository;

import me.chiqors.minimarket_backend.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerCode(String customerCode);

    Page<Customer> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    Customer findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT c.name, c.customer_code, c.phone_number, c.address, c.gender, c.birth_date, MAX(t.created_at) AS created_at, MAX(t.updated_at) AS updated_at, SUM(t.total_price) AS total_price, SUM(td.quantity) AS total_purchased FROM customers c JOIN transactions t ON c.id = t.customer_id JOIN transaction_details td ON t.id = td.transaction_id WHERE t.created_at BETWEEN :startDate AND :endDate GROUP BY c.id ORDER BY total_purchased DESC",
            countQuery = "SELECT COUNT(*) FROM customers c JOIN transactions t ON c.id = t.customer_id JOIN transaction_details td ON t.id = td.transaction_id WHERE t.created_at BETWEEN :startDate AND :endDate GROUP BY c.id",
            nativeQuery = true)
    Page<Object[]> findAllCustomerTransactionSummary(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
}