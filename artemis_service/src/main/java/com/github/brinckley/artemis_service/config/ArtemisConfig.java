package com.github.brinckley.artemis_service.config;

import com.github.brinckley.artemis_service.exception.ArtemisException;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Slf4j
@EnableJms
@Configuration
@RequiredArgsConstructor
public class ArtemisConfig {
    private static final int RETRY_INTERVAL = 1000;

    private static final double RETRY_INTERVAL_MULTIPLIER = 1.5;

    private static final int MAX_RETRY_INTERVAL = 60000;

    private static final int RECONNECT_ATTEMPTS = -1;

    private final ArtemisProperties artemisProperties;

    @Bean
    public ConnectionFactory connectionFactory() throws ArtemisException {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

            connectionFactory.setBrokerURL(artemisProperties.getBrokerUrl());
            connectionFactory.setUser(artemisProperties.getUser());
            connectionFactory.setPassword(artemisProperties.getPassword());

            connectionFactory.setRetryInterval(RETRY_INTERVAL);
            connectionFactory.setRetryIntervalMultiplier(RETRY_INTERVAL_MULTIPLIER);
            connectionFactory.setMaxRetryInterval(MAX_RETRY_INTERVAL);
            connectionFactory.setReconnectAttempts(RECONNECT_ATTEMPTS);

            return connectionFactory;
        } catch (JMSException e) {
            log.error("Cannot create ConnectionFactory {}", e.getMessage());
            throw new ArtemisException("Cannot create connection factory error", e);
        }
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory,
                                   MappingJackson2MessageConverter mappingJackson2MessageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(mappingJackson2MessageConverter);
        jmsTemplate.setPubSubDomain(false);

        try (Connection connection = connectionFactory.createConnection()){
            connection.start();
            log.info("Connection successfully started");
        } catch (JMSException e) {
            log.error("Cannot create JmsTemplate {}", e.getMessage());
            throw new ArtemisException("Unable to settle jms connection", e);
        }

        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        defaultJmsListenerContainerFactory.setConcurrency("1-1");
        defaultJmsListenerContainerFactory.setPubSubDomain(false);

        return defaultJmsListenerContainerFactory;
    }

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
