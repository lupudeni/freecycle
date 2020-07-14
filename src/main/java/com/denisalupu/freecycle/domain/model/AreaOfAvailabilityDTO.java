package com.denisalupu.freecycle.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaOfAvailabilityDTO {
   //TODO:take this out of equals and hashcode?
    private long id;

    private String country;

    private String city;

    //TODO:take this out of equals and hashcode?
    private List<DonationDTO> donations;

}
