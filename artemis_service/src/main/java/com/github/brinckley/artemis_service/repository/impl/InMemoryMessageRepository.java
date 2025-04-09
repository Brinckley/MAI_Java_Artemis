package com.github.brinckley.artemis_service.repository.impl;

import com.github.brinckley.artemis_service.model.OutputMessage;
import com.github.brinckley.artemis_service.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class InMemoryMessageRepository implements MessageRepository {
    private final Queue<OutputMessage> storage = new ConcurrentLinkedQueue<>();

    public void save(OutputMessage outputMessage) {
        storage.add(outputMessage);
    }

    public Optional<OutputMessage> getLatest() {
        OutputMessage latest = storage.peek();
        return latest == null ? Optional.empty() : Optional.of(latest);
    }
}
