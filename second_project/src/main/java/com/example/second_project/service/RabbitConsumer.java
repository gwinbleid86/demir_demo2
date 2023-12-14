package com.example.second_project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@EnableRabbit
@Service
@RequiredArgsConstructor
public class RabbitConsumer {

    private final MainService mainService;

    @RabbitListener(queues = "myQueue")
    public void listen(String message) {
        mainService.handleData(message);
    }
}
