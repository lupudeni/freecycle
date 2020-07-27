package com.denisalupu.freecycle.service;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public <T> String getMessage(String code, List<T> args) {
        return messageSource.getMessage(code, args.toArray(), Locale.ENGLISH);
    }
}
