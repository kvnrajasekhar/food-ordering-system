package com.feast.server_main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feast.server_main.model.Delivery;
import com.feast.server_main.repository.DeliveryRepository;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;

    public Delivery assignDelivery(Delivery delivery) {
        delivery.setStatus("In Progress");
        return deliveryRepository.save(delivery);
    }

    public Delivery updateStatus(Integer deliveryId, String status) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
        delivery.setStatus(status);
        return deliveryRepository.save(delivery);
    }
}

