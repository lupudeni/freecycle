/**
 * Mappers created using the implementation of ModelMapper: 'org.modelmapper.extensions:modelmapper-spring:2.3.0'
 * http://modelmapper.org
 */

package com.denisalupu.freecycle.mapper;

import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.DonationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EntityToDTOMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public DonationDTO donationMapper(DonationEntity source) {
        return modelMapper.map(source, DonationDTO.class);
    }

    public UserDTO userMapper(UserEntity source) {
        return modelMapper.map(source, UserDTO.class);
    }
}
