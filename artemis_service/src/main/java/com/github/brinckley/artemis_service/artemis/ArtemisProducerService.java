package com.github.brinckley.artemis_service.artemis;

import com.github.brinckley.artemis_service.config.ArtemisProperties;
import com.github.brinckley.artemis_service.model.InputMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtemisProducerService {
    private final ArtemisProperties artemisProperties;

    private final JmsTemplate jmsTemplate;

    public void sendMessage(String inputMessage) {
        log.info("Sending message to Artemis {}", inputMessage);
        jmsTemplate.convertAndSend(artemisProperties.getInputQueueName(), inputMessage);
        log.info("Successfully sent message {} to Artemis queue {}", inputMessage,
                artemisProperties.getInputQueueName());
    }
}

