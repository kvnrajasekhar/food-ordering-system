package com.feast.server_main.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PaymentId")
    private Integer paymentId;

    @OneToOne
    @JoinColumn(name = "OrderId", nullable = false, unique = true)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @Column(name = "PaymentMethod", columnDefinition = "VARCHAR(255)")
    private String paymentMethod;

    @Column(name = "TransactionId", columnDefinition = "NUMBER(19, 0)")
    private Long transactionId;

    @Column(name = "Amount")
    private java.math.BigDecimal amount; // Recommended for currency

    @Column(name = "Status", columnDefinition = "VARCHAR(255)")
    private String status;

    // Constructors
    public Payment() {
    }

    public Payment(Order order, Customer customer, String paymentMethod, Long transactionId, BigDecimal amount, String status) {
        this.order = order;
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
    }

    // Getters
    public Integer getPaymentId() {
        return paymentId;
    }

    public Order getOrderStatus() {
        return order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public void setOrderStatus(Order order) {
        this.order = order;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", orderStatus=" + (order != null ? order.getOrderId() : null) +
                ", customer=" + (customer != null ? customer.getCustomerId() : null) +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", transactionId=" + transactionId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId);
    }
}