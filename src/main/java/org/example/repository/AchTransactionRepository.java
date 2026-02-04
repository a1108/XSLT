package org.example.repository;

import org.example.model.AchTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchTransactionRepository extends JpaRepository<AchTransaction, String> {
}
