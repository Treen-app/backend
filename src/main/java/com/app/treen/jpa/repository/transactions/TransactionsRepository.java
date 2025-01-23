package com.app.treen.jpa.repository.transactions;

import com.app.treen.transactions.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {



}
