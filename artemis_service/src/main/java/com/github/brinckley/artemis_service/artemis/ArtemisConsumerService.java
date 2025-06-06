package com.github.brinckley.artemis_service.artemis;

import com.github.brinckley.artemis_service.model.InputMessage;
import com.github.brinckley.artemis_service.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtemisConsumerService {
    private final MessageRepository messageRepository;

    @Value("${app.queues.output}")
    private String outputQueue;


    @JmsListener(destination = "${app.queues.output}")
    public void receiveMessage(InputMessage inputMessage) {
        log.info("Received message : {} from queue {}", inputMessage, outputQueue);
        messageRepository.save(inputMessage);
    }
}
