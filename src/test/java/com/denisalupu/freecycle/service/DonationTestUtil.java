package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.CategoryName;
import com.denisalupu.freecycle.domain.Status;
import com.denisalupu.freecycle.domain.entity.AreaOfAvailabilityEntity;
import com.denisalupu.freecycle.domain.entity.CategoryEntity;
import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.AreaOfAvailabilityDTO;
import com.denisalupu.freecycle.domain.model.CategoryDTO;
import com.denisalupu.freecycle.domain.model.DonationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;

public class DonationTestUtil {

    public DonationEntity getDonationEntity() {
        AreaOfAvailabilityEntity areaEntity = getAreaOfAvailabilityEntity();

        CategoryEntity categoryEntity = getCategoryEntity();

        return DonationEntity.builder()
                .id(1L)
                .description("2015")
                .status(Status.AVAILABLE)
                .title("Fallout 4")
                .area(areaEntity)
                .category(categoryEntity)
                .donor(getUserEntity())
                .userRequests(new HashSet<>())
                .build();
    }


    public void add4UserRequests(Set<UserEntity> userRequests) {
        UserEntity user1Mock = mock(UserEntity.class);
        UserEntity user2Mock = mock(UserEntity.class);
        UserEntity user3Mock = mock(UserEntity.class);
        UserEntity user4Mock = mock(UserEntity.class);
        userRequests.add(user1Mock);
        userRequests.add(user2Mock);
        userRequests.add(user3Mock);
        userRequests.add(user4Mock);
    }

    public void add5UserRequests(Set<UserEntity> userRequests) {
        add4UserRequests(userRequests);
        UserEntity user5Mock = mock(UserEntity.class);
        userRequests.add(user5Mock);

    }


    public CategoryEntity getCategoryEntity() {
        return CategoryEntity.builder()
                .id(7L)
                .categoryName(CategoryName.GAMES)
                .build();
    }

    public AreaOfAvailabilityEntity getAreaOfAvailabilityEntity() {
        return AreaOfAvailabilityEntity.builder()
                .id(1L)
                .city("Bucharest")
                .country("Romania")
                .build();
    }

    public DonationDTO getDonationDTO() {
        AreaOfAvailabilityDTO areaDTO = getAreaOfAvailabilityDTO();

        CategoryDTO categoryDTO = getCategoryDTO();

        return DonationDTO.builder()
                .description("2015")
                .status(Status.AVAILABLE)
                .title("Fallout 4")
                .area(areaDTO)
                .category(categoryDTO)
                .build();
    }

    public CategoryDTO getCategoryDTO() {
        return CategoryDTO.builder()
                    .categoryName(CategoryName.GAMES)
                    .build();
    }

    public AreaOfAvailabilityDTO getAreaOfAvailabilityDTO() {
        return AreaOfAvailabilityDTO.builder()
                    .city("Bucharest")
                    .country("Romania")
                    .build();
    }

    public UserDTO getUserDTO() {
        return UserDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .phone("0740598119")
                .email("john.doe@freecycle.com")
                .build();
    }


    public UserEntity getUserEntity() {
        return UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .phone("0740598119")
                .email("john.doe@freecycle.com")
                .build();
    }


}
