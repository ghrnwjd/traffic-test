package com.example.demo.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestConsumer {

    @KafkaListener(topics = "topic", groupId = "test_group")
    public void listener(Object data) {
        System.out.println(data);
    }
}
