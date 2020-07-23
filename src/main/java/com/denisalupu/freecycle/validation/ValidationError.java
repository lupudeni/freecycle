package com.denisalupu.freecycle.validation;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError {

    private String fieldName;

    private String message;
}
