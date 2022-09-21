package com.example.Coffee.repository;

import com.example.Coffee.entities.PaymentForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentFormRepository extends JpaRepository<PaymentForm, Long> {
    List<PaymentForm> findAll();
}
