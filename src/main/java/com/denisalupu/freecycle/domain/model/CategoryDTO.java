package com.denisalupu.freecycle.domain.model;

import com.denisalupu.freecycle.domain.CategoryName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private long id;

    private CategoryName categoryName;

}
