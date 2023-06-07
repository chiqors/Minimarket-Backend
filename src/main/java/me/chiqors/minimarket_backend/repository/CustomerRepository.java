package me.chiqors.minimarket_backend.repository;

import me.chiqors.minimarket_backend.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerCode(String customerCode);

    Page<Customer> findAllByNameContaining(String name, Pageable pageable);
}