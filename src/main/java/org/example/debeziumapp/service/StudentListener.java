package org.example.debeziumapp.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StudentListener {

    @KafkaListener(topics = "postgres.public.student", groupId = "student")
    public void listen(String data) {
        System.out.println(data);
    }
}
