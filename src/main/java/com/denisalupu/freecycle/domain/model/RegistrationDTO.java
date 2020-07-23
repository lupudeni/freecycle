package com.denisalupu.freecycle.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String userName;

    private String password;
}
