package com.denisalupu.freecycle.domain.model;

import com.denisalupu.freecycle.validation.ValidEmail;
import com.denisalupu.freecycle.validation.ValidPhone;
import com.denisalupu.freecycle.validation.ValidUserName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {

    private String firstName;

    private String lastName;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    @ValidPhone
    private String phone;

    @NotNull
    @NotEmpty
    @ValidUserName
    private String userName;

    private String password;
}
