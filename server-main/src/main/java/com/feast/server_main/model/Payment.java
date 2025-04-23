package com.feast.server_main.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PaymentId")
    private Integer paymentId;

    @OneToOne
    @JoinColumn(name = "OrderId", nullable = false, unique = true)
    private Order order; 

    @Column(name = "PaymentMethod", columnDefinition = "VARCHAR(255)")
    private String paymentMethod;

    @Column(name = "TransactionId")
    private Integer transactionId;

    @Column(name = "Amount")
    private Double amount;

    public Payment() {
    }

    public Payment(Integer paymentId, Order order, String paymentMethod, Integer transactionId, Double amount) {
        this.paymentId = paymentId;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId + ", order=" + order + ", paymentMethod=" + paymentMethod + ", transactionId=" + transactionId + ", amount=" + amount + "]";
    }
}