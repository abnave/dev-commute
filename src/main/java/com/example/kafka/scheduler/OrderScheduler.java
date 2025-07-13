package com.example.kafka.scheduler;

import com.example.kafka.model.OrderRequest;
import com.example.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@EnableScheduling
public class OrderScheduler {

    @Autowired
    private KafkaProducerService producerService;

    private final Random random = new Random();
    private final String[] products = {"Widget", "Gadget", "Thingamajig", "Doohickey"};

    @Scheduled(fixedRate = 2000) // every 2 seconds
    public void publishRandomOrder() {
        OrderRequest order = new OrderRequest();
        order.setOrderId(String.valueOf(System.currentTimeMillis()));
        order.setUserId("user" + (random.nextInt(100) + 1));
        order.setProduct(products[random.nextInt(products.length)]);
        order.setQuantity(random.nextInt(10) + 1);
        order.setPrice(Math.round((random.nextDouble() * 1000) * 100.0) / 100.0);
        producerService.sendOrder(order);
    }
}
