package com.denisalupu.freecycle.domain.model;

import com.denisalupu.freecycle.domain.Status;
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
public class DonationDTO {

    private long id;

    private CategoryDTO category;

    private String title;

    private String description;

    private AreaOfAvailabilityDTO area;

    private Status status;

    private UserDTO donor;

    private UserDTO receiver;

    private List<Long> pictureIds;

    private Set<UserDTO> userRequests;

    //TODO timestamp posted
    //TODO timestamp last edited?


}
