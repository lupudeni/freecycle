package com.denisalupu.freecycle.domain.model;

import com.denisalupu.freecycle.validation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long id;

    private String firstName;

    private String lastName;


    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    private String phone;

    @JsonIgnore
    private List<DonationDTO> donatedObjects;

//    @JsonIgnore
//    private List<DonationDTO> receivedObjects;

    @JsonIgnore
    private Set<DonationDTO> donationRequests;

}
