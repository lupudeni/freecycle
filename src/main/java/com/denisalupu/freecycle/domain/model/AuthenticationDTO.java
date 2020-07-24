package com.denisalupu.freecycle.domain.model;

import com.denisalupu.freecycle.validation.ValidUserName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTO {

    private String id;

    @NotNull
    @NotEmpty
    @ValidUserName
    private String userName;

    private String password;

//    @JsonIgnore
//    private UserDTO userDTO;
}
