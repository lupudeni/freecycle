package com.denisalupu.freecycle.mapper;

import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.PictureEntity;
import com.denisalupu.freecycle.domain.model.DonationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mappers created using the implementation of ModelMapper: 'org.modelmapper.extensions:modelmapper-spring:2.3.0'
 * http://modelmapper.org
 */
@Component
public class Mapper {
    private ModelMapper modelMapper = new ModelMapper();

    public <T, R> R map(T source, Class<R> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }

    public <T, R> List<R> mapCollectionToList(Collection<T> source, Class<R> destinationClass) {
        return source.stream()
                .map(element -> map(element, destinationClass))
                .collect(Collectors.toList());
    }

    public List<DonationDTO> mapDonationDtos(List<DonationEntity> donationEntities) {
        return donationEntities.stream()
                .map(this::mapDonationDto)
                .collect(Collectors.toList());
    }

    public DonationDTO mapDonationDto(DonationEntity donationEntity) {
        DonationDTO dto = map(donationEntity, DonationDTO.class);
        List<Long> pictureIds = donationEntity.getPictures().stream()
                .map(PictureEntity::getId)
                .collect(Collectors.toList());
        dto.setPictureIds(pictureIds);
        return dto;
    }
}
