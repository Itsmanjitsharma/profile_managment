package com.cca.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cca.dashboard.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer>{

}
