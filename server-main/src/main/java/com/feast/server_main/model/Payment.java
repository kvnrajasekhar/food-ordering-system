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
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "OrderId", nullable = false, unique = true)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "CustomerId", nullable = false)
    private User customer;

    @Column(name = "PaymentMethod", columnDefinition = "VARCHAR(255)")
    private String paymentMethod;

    @Column(name = "TransactionId")
    private Long transactionId;

    @Column(name = "Amount")
    private BigDecimal amount; // Recommended for currency

    @Column(name = "Status", columnDefinition = "VARCHAR(255)")
    private String status;

    public Payment() {
    }

    public Payment(Order order, User customer, String paymentMethod, Long transactionId, BigDecimal amount, String status) {
        this.order = order;
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", order=" + (order != null ? order.getOrderId() : null) +
                ", customer=" + (customer != null ? customer.getCustomerId() : null) +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", transactionId=" + transactionId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}