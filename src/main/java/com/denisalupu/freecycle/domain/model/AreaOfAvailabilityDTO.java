package com.denisalupu.freecycle.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private long id;

    private String country;

    private String city;

    @JsonIgnore
    private List<DonationDTO> donations;

}
