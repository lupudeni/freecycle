package com.denisalupu.freecycle.exception;

import org.springframework.core.NestedRuntimeException;

public class EntityNotFoundException extends NestedRuntimeException {

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
