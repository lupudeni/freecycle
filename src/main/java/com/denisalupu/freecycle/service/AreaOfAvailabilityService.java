package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.model.AreaOfAvailabilityDTO;
import com.denisalupu.freecycle.mapper.EntityToDTOMapper;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.AreaOfAvailabilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AreaOfAvailabilityService {
    private final AreaOfAvailabilityRepository areaRepository;
    private final EntityToDTOMapper entityToDTOMapper;
    private final Mapper mapper;

    public List<AreaOfAvailabilityDTO> getAllAreas() {
        return mapper.mapCollectionToList(areaRepository.findAll(), AreaOfAvailabilityDTO.class);
    }
}
