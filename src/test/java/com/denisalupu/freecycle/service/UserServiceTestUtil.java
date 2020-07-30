package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.UserDTO;

public class UserServiceTestUtil {

    public UserDTO getUserDTO() {
        return UserDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@freecycletest.com")
                .phone("0740598119")
                .build();
    }

    public UserEntity getUserEntity() {
        return UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@freecycletest.com")
                .phone("0740598119")
                .build();
    }
}
