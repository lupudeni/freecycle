/**
 * Mappers created using the implementation of ModelMapper: 'org.modelmapper.extensions:modelmapper-spring:2.3.0'
 * http://modelmapper.org
 */
package com.denisalupu.freecycle.mapper;

import com.denisalupu.freecycle.domain.entity.AreaOfAvailabilityEntity;
import com.denisalupu.freecycle.domain.entity.CategoryEntity;
import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.AreaOfAvailabilityDTO;
import com.denisalupu.freecycle.domain.model.CategoryDTO;
import com.denisalupu.freecycle.domain.model.DonationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DTOToEntityMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public DonationEntity donationMapper(DonationDTO donationDTO) {
        return modelMapper.map(donationDTO, DonationEntity.class);
    }

    public UserEntity userMapper(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    public AreaOfAvailabilityEntity areaMapper(AreaOfAvailabilityDTO areaOfAvailabilityDTO) {
        return modelMapper.map(areaOfAvailabilityDTO, AreaOfAvailabilityEntity.class);
    }

    public CategoryEntity categoryMapper(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, CategoryEntity.class);
    }


}
