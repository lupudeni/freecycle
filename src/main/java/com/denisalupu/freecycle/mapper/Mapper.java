/**
 * Mappers created using the implementation of ModelMapper: 'org.modelmapper.extensions:modelmapper-spring:2.3.0'
 * http://modelmapper.org
 */

package com.denisalupu.freecycle.mapper;

import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.AreaOfAvailabilityDTO;
import com.denisalupu.freecycle.domain.model.DonationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public <T, R> Set<R> mapCollectionToSet(Collection<T> source, Class<R> destinationClass) {
        return source.stream()
                .map(element -> map(element, destinationClass))
                .collect(Collectors.toSet());
    }

}
