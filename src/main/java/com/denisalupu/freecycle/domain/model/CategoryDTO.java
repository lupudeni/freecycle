package com.denisalupu.freecycle.domain.model;

import com.denisalupu.freecycle.utils.CategoryName;
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
public class CategoryDTO {

    private long id;

    private CategoryName categoryName;

    //do i have to have a list for this?
    private List<DonationDTO> donations;

}
