package com.denisalupu.freecycle.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DonationDTO> donations;

}
