package com.denisalupu.freecycle.domain.model;

import com.denisalupu.freecycle.domain.Status;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationDTO {

    private long id;

    private CategoryDTO category;

    private String title;

    private String description;

    private AreaOfAvailabilityDTO area;

    private Status status;

    private UserDTO donor;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Long> pictureIds;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserDTO> userRequests;

    //TODO timestamp posted


}
