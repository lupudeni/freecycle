package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.AreaOfAvailabilityEntity;
import com.denisalupu.freecycle.domain.model.AreaOfAvailabilityDTO;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.AreaOfAvailabilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AreaOfAvailabilityService {

    private final AreaOfAvailabilityRepository areaRepository;

    private final Mapper mapper;

    public List<AreaOfAvailabilityDTO> getAllAreas() {
        return mapper.mapCollectionToList(areaRepository.findAll(), AreaOfAvailabilityDTO.class);
    }

    public AreaOfAvailabilityEntity getEntityById(long id) {
        return areaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not a valid area"));
    }
}
