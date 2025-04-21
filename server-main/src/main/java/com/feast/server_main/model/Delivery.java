package com.feast.server_main.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "delivery_status")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "deliveryId")
    private Long deliveryId;

    @OneToOne
    @JoinColumn(name = "orderId", nullable = false, unique = true)
    private Order order;

    @Column(name = "agentId")
    private Integer agentId;

    @Column(name = "Status", columnDefinition = "VARCHAR(255)")
    private String status;

    @Column(name = "ETA")
    private LocalDateTime eta;

    public Delivery() {
    }

    public Delivery(Order order, Integer agentId, String status, LocalDateTime eta) {
        this.order = order;
        this.agentId = agentId;
        this.status = status;
        this.eta = eta;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getEta() {
        return eta;
    }

    public void setEta(LocalDateTime eta) {
        this.eta = eta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(deliveryId, delivery.deliveryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId);
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "deliveryId=" + deliveryId +
                ", order=" + (order != null ? order.getOrderId() : null) +
                ", agentId=" + agentId +
                ", status='" + status + '\'' +
                ", eta=" + eta +
                '}';
    }
}