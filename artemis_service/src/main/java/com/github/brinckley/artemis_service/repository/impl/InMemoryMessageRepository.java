package com.github.brinckley.artemis_service.repository.impl;

import com.github.brinckley.artemis_service.model.InputMessage;
import com.github.brinckley.artemis_service.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class InMemoryMessageRepository implements MessageRepository {
    private final Queue<InputMessage> storage = new ConcurrentLinkedQueue<>();

    public void save(InputMessage inputMessage) {
        storage.add(inputMessage);
    }

    public Optional<InputMessage> getLatest() {
        InputMessage latest = storage.peek();
        return latest == null ? Optional.empty() : Optional.of(latest);
    }
}
