package com.github.brinckley.artemis_service.repository;

import com.github.brinckley.artemis_service.model.InputMessage;

import java.util.Optional;

public interface MessageRepository {
    void save(InputMessage outputMessage);

    Optional<InputMessage> getLatest();
}
