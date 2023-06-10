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

    // Query to get customer data with total transaction and total price
    // column: c.name, c.customer_code, c.phone_number, c.address, c.gender, c.birth_date, c.created_at, c.updated_at, SUM(t.total_price) AS total_price, COUNT(t.customer_id) AS total_purchased
    @Query(value = "SELECT c.name, c.customer_code, c.phone_number, c.address, c.gender, c.birth_date, c.created_at, c.updated_at, SUM(t.total_price) AS total_price, COUNT(t.customer_id) AS total_purchased FROM customers c JOIN transactions t ON c.id = t.customer_id WHERE t.created_at BETWEEN :startDate AND :endDate GROUP BY c.id", nativeQuery = true)
    List<Object[]> getCustomerByTransactionDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}