package com.denisalupu.freecycle.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String userName;

    private String email;

    private String phone;

    @JsonIgnore
    private List<DonationDTO> donatedObjects;

    @JsonIgnore
    private List<DonationDTO> receivedObjects;

    @JsonIgnore
    private Set<DonationDTO> donationRequests;

}
