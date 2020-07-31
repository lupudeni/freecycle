package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.TestUtil;
import com.denisalupu.freecycle.domain.entity.AreaOfAvailabilityEntity;
import com.denisalupu.freecycle.domain.model.AreaOfAvailabilityDTO;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.AreaOfAvailabilityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class AreaOfAvailabilityServiceTest {
    @Mock
    private AreaOfAvailabilityRepository areaRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private AreaOfAvailabilityService sut;

    private TestUtil testUtil = new TestUtil();

    private AreaOfAvailabilityEntity areaEntity;

    private AreaOfAvailabilityDTO areaDTO;

    @BeforeEach
    void setUp() {
        areaEntity = testUtil.getAreaOfAvailabilityEntity();
        areaDTO = testUtil.getAreaOfAvailabilityDTO();
    }
    @Test
    void given_areas_when_getAllAreas_then_return_list_of_areas() {
        //given
        var areaEntityMock = mock(AreaOfAvailabilityEntity.class);
        List<AreaOfAvailabilityEntity> areaEntityList = List.of(areaEntityMock);
        when(areaRepository.findAll()).thenReturn(areaEntityList);
        List<AreaOfAvailabilityDTO> areaDTOList = List.of(areaDTO);
        when(mapper.mapCollectionToList(areaEntityList, AreaOfAvailabilityDTO.class))
                .thenReturn(areaDTOList);

        //when
        List<AreaOfAvailabilityDTO> returnedList = sut.getAllAreas();

        //then
        assertThat(returnedList).isEqualTo(areaDTOList);
    }

    @Test
    void given_id_when_getEntityById_then_return_area_entity() {
        //given
        long id = 1L;
        when(areaRepository.findById(id)).thenReturn(Optional.of(areaEntity));

        //when
        var actualAreaEntity = sut.getEntityById(id);

        //then
        assertThat(actualAreaEntity).isSameAs(areaEntity);
    }

    @Test
    void given_non_existent_id_when_getEntityById_then_throw_exception() {
        //given
        long id = 1L;
        when(areaRepository.findById(id)).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(EntityNotFoundException.class, () -> sut.getEntityById(id));

    }
}