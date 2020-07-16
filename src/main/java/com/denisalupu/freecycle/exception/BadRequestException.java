package com.denisalupu.freecycle.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}
