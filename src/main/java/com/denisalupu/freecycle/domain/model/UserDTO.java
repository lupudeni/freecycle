package com.denisalupu.freecycle.domain.model;

import com.denisalupu.freecycle.validation.ValidEmail;
import com.denisalupu.freecycle.validation.ValidPhone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @ValidPhone
    @NotNull
    @NotEmpty
    private String phone;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DonationDTO> donatedObjects;


    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DonationDTO> donationRequests;

}
