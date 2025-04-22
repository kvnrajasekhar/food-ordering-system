package com.feast.server_main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feast.server_main.model.Payment;
import com.feast.server_main.repository.PaymentRepository;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(Payment payment) {
        payment.setStatus("Successful");
        return paymentRepository.save(payment);
    }
}
