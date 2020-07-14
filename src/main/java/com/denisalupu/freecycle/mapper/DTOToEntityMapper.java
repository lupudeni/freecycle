package com.denisalupu.freecycle.mapper;

import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.model.DonationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DTOToEntityMapper {
   private ModelMapper modelMapper = new ModelMapper();

   public DonationEntity donationMapper(DonationDTO donationDTO) {
     return  modelMapper.map(donationDTO, DonationEntity.class);
   }
}
