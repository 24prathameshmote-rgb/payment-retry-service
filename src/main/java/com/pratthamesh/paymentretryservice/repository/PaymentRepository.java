package com.pratthamesh.paymentretryservice.repository;

import com.pratthamesh.paymentretryservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

// handles database operations for payments
public interface PaymentRepository extends JpaRepository<Payment, Long>
{

}

