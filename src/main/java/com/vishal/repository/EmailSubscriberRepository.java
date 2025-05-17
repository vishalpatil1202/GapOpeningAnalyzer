package com.vishal.repository;

import com.vishal.model.EmailSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSubscriberRepository extends JpaRepository<EmailSubscriber, Long> {
    boolean existsByEmail(String email); 
}