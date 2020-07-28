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

public class DonationTestUtil {

    public DonationEntity getBasicDonationEntity() {
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
                .userRequests(new HashSet<UserEntity>())
                .build();
    }


    public DonationEntity getFullyRequestedDonationEntity() {
        AreaOfAvailabilityEntity areaEntity = getAreaOfAvailabilityEntity();
        CategoryEntity categoryEntity = getCategoryEntity();

        UserEntity userEntity3 = UserEntity.builder()
                .id(3L)
                .build();
        UserEntity userEntity4 = UserEntity.builder()
                .id(4L)
                .build();
        UserEntity userEntity5 = UserEntity.builder()
                .id(5L)
                .build();
        UserEntity userEntity6 = UserEntity.builder()
                .id(6L)
                .build();
        UserEntity userEntity7 = UserEntity.builder()
                .id(7L)
                .build();

        Set<UserEntity> requests = Set.of(userEntity3, userEntity4, userEntity5, userEntity6, userEntity7);

        return DonationEntity.builder()
                .id(1L)
                .description("2015")
                .status(Status.FULLY_REQUESTED)
                .title("Fallout 4")
                .area(areaEntity)
                .category(categoryEntity)
                .donor(getUserEntity())
                .userRequests(requests)
                .build();
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
