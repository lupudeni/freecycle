package com.denisalupu.freecycle.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

//    private UserDTO userDTO;
//
//    private DonationDTO donationDTO;

    private Long userId;

    private Long donationId;
}
