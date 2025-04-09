package com.github.brinckley.artemis_service.repository;

import com.github.brinckley.artemis_service.model.OutputMessage;

import java.util.Optional;

public interface MessageRepository {
    void save(OutputMessage outputMessage);

    Optional<OutputMessage> getLatest();
}
