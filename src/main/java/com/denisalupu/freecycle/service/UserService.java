package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.UserRepository;
import com.denisalupu.freecycle.utils.SortOrder;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserDTO create(UserDTO userDTO) {
        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
        UserEntity savedEntity = userRepository.save(userEntity);
        return mapper.map(savedEntity, UserDTO.class);
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
        return mapper.map(existingUserEntity, UserDTO.class);
    }

    public UserEntity findEntityById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User entity with id '" + id + "' not found"));
    }

    public UserDTO findById(long id) {
        UserEntity userEntity = findEntityById(id);
        return mapper.map(userEntity, UserDTO.class);
    }

    public UserDTO findByUserName(String userName) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(userName);
        UserEntity userEntity = userEntityOptional.orElseThrow(
                () -> new EntityNotFoundException("User entity with user name '" + userName + "' not found"));
        return mapper.map(userEntity, UserDTO.class);
    }

    public List<UserDTO> getAllOrderedById(SortOrder sortOrder) {
        List<UserEntity> userEntities;
        if(sortOrder == SortOrder.ASC) {
            userEntities = userRepository.findAllByOrderByIdAsc();
        } else {
            userEntities = userRepository.findAllByOrderByIdDesc();
        }
        return mapper.mapCollectionToList(userEntities, UserDTO.class);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }


}
