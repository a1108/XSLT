package org.example.config;

import org.example.model.AchTransaction;
import org.example.repository.AchTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initDatabase(AchTransactionRepository repository) {
        return args -> {
            logger.info("Initializing in-memory database with ACH transaction data");
            
            repository.save(new AchTransaction("ACH-2024-001", "021000021", "091000022", 
                new BigDecimal("15000.00"), "2024-02-01"));
            logger.debug("Saved transaction: ACH-2024-001");
            
            repository.save(new AchTransaction("ACH-2024-002", "021000089", "091000019", 
                new BigDecimal("30000.00"), "2024-02-03"));
            logger.debug("Saved transaction: ACH-2024-002");
            
            repository.save(new AchTransaction("ACH-2024-003", "021000090", "091000020", 
                new BigDecimal("40000.00"), "2024-02-03"));
            logger.debug("Saved transaction: ACH-2024-003");
            
            repository.save(new AchTransaction("ACH-2024-004", "021000091", "091000021", 
                new BigDecimal("10000.00"), "2024-02-03"));
            logger.debug("Saved transaction: ACH-2024-004");
            
            repository.save(new AchTransaction("ACH-2024-005", "021000092", "091000022", 
                new BigDecimal("50000.00"), "2024-02-03"));
            logger.debug("Saved transaction: ACH-2024-005");
            
            long count = repository.count();
            logger.info("Database initialization completed. Total transactions loaded: {}", count);
        };
    }
}
