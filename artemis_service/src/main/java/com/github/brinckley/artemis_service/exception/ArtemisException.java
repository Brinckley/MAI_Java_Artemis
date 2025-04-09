package com.github.brinckley.artemis_service.exception;

import lombok.experimental.StandardException;

import java.io.Serial;

@StandardException
public class ArtemisException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 292796551088212L;

    public ArtemisException(String message) {
        super(message);
    }

    public static ArtemisException format(String message, Object ... objects) {
        return new ArtemisException(String.format(message, objects));
    }
}
