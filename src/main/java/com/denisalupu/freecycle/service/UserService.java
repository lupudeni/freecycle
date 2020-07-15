package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.DonationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.mapper.DTOToEntityMapper;
import com.denisalupu.freecycle.mapper.EntityToDTOMapper;
import com.denisalupu.freecycle.repository.UserRepository;
import com.denisalupu.freecycle.utils.SortOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final EntityToDTOMapper entityToDTOMapper;
    private final DTOToEntityMapper dtoToEntityMapper;
    private final UserRepository userRepository;

    public UserDTO create(UserDTO userDTO) {
        UserEntity userEntity = dtoToEntityMapper.userMapper(userDTO);
        UserEntity savedEntity = userRepository.save(userEntity);
        return entityToDTOMapper.userMapper(savedEntity);
    }

    //TODO: create a change password method that updates only the password field

    @Transactional
    public UserDTO update(UserDTO userDTO) {
        UserEntity existingUserEntity = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return updateFields(existingUserEntity, userDTO);
    }


    //TODO: password should have a special update method,
    // also the password should not be sent back???


    private UserDTO updateFields(UserEntity existingUserEntity, UserDTO userDTO) {
            existingUserEntity.setFirstName(userDTO.getFirstName());
            existingUserEntity.setLastName(userDTO.getLastName());
            existingUserEntity.setUserName(userDTO.getUserName());
            existingUserEntity.setEmail(userDTO.getEmail());
            existingUserEntity.setPhone(userDTO.getPhone());
        return entityToDTOMapper.userMapper(existingUserEntity);
    }


    public UserDTO findById(long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        UserEntity userEntity = userEntityOptional.orElseThrow(
                () -> new EntityNotFoundException("User entity with id '" + id + "' not found"));
        return entityToDTOMapper.userMapper(userEntity);
    }

    public UserDTO findByUserName(String userName) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(userName);
        UserEntity userEntity = userEntityOptional.orElseThrow(
                () -> new EntityNotFoundException("User entity with user name '" + userName + "' not found"));
        return entityToDTOMapper.userMapper(userEntity);
    }

    public List<UserDTO> getAllOrderedById(SortOrder sortOrder) {
        List<UserEntity> userEntities;
        if(sortOrder == SortOrder.ASC) {
            userEntities = userRepository.findAllByOrderByIdAsc();
        } else {
            userEntities = userRepository.findAllByOrderByIdDesc();
        }
        return userEntities.stream()
                .map(entityToDTOMapper::userMapper)
                .collect(Collectors.toList());
    }

//    public List<UserDTO> getAllOrderedByDonatedObjects(SortOrder sortOrder) {
//        List<UserEntity> userEntities;
//        if(sortOrder == SortOrder.ASC) {
//            userEntities = userRepository.findAllByOrderByDonatedObjectsAsc();
//        } else {
//            userEntities = userRepository.findAllByOrderByDonatedObjectsDesc();
//        }
//        return userEntities.stream()
//                .map(entityToDTOMapper::userMapper)
//                .collect(Collectors.toList());
//    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }


}
