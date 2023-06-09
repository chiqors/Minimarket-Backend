package me.chiqors.minimarket_backend.repository;

import me.chiqors.minimarket_backend.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByTransactionCode(String transactionCode);

    Page<Transaction> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Transaction> findByCreatedAtBetweenOrderByCreatedAtDesc(Date createdAt, Date createdAt2, Pageable pageable);

    @Query(value = "SELECT SUM(total_price) FROM transactions WHERE created_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    Double sumTotalPrice(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
