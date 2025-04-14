package com.github.brinckley.artemis_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@Jacksonized
@Validated
@AllArgsConstructor
public class InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = -163724222695237462L;

    @JsonProperty("message")
    @NotNull(message = "Message cannot be null")
    private String message;
}
