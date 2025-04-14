package com.github.brinckley.artemis_service.controller;

import com.github.brinckley.artemis_service.artemis.ArtemisProducerService;
import com.github.brinckley.artemis_service.model.InputMessage;
import com.github.brinckley.artemis_service.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/artemis")
@RequiredArgsConstructor
public class ArtemisController {
    private final ArtemisProducerService artemisProducerService;

    private final MessageRepository messageRepository;

    @PostMapping("/produce")
    public ResponseEntity<String> produceMessage(@Validated @RequestBody InputMessage producedMessage) {
        artemisProducerService.sendMessage(producedMessage);

        return ResponseEntity.ok()
                .body("Successfully sent the message to Artemis");
    }

    @GetMapping("/consume")
    public ResponseEntity<InputMessage> consumeMessage() {
        Optional<InputMessage> latest = messageRepository.getLatest();

        if (latest.isPresent()) {
            return ResponseEntity.ok()
                    .body(latest.get());
        }

        InputMessage inputMessage = InputMessage.builder().message("No messages in the storage yet").build();
        return ResponseEntity.ok()
                .body(inputMessage);
    }
}
