package me.chiqors.minimarket_backend.repository;

import me.chiqors.minimarket_backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByTransactionCode(String transactionCode);

    List<Transaction> findByCreatedAtBetween(Date createdAt, Date createdAt2);
}
