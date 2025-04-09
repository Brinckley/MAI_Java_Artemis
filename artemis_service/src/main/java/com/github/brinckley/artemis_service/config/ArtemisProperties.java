package com.github.brinckley.artemis_service.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ArtemisProperties {
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${app.queues.input}")
    private String inputQueueName;

    @Value("${app.queues.output}")
    private String outputQueueName;
}
