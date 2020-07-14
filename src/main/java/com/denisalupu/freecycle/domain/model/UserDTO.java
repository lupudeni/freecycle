package com.denisalupu.freecycle.domain.model;

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

    private String password;

    private String email;

    private String phone;

    private List<DonationDTO> donatedObjects;

    private List<DonationDTO> receivedObjects;


}
