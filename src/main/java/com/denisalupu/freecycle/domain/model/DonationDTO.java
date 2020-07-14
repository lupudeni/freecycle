package com.denisalupu.freecycle.domain.model;

import com.denisalupu.freecycle.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private List<PictureDTO> pictures;


    //timestamp posted
    //timestamp last edited
}
