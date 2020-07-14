package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.mapper.EntityToDTOMapper;
import com.denisalupu.freecycle.repository.UserRepository;
import com.sun.xml.bind.v2.TODO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final EntityToDTOMapper mapper;
    private final UserRepository userRepository;

    public UserDTO findById(long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        UserEntity userEntity = userEntityOptional.orElseThrow(
                () -> new EntityNotFoundException("User entity with id '" + id + "' not found"));
        return mapper.userMapper(userEntity);
    }

    public UserDTO findByUserName(String userName) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(userName);
        UserEntity userEntity = userEntityOptional.orElseThrow(
                () -> new EntityNotFoundException("User entity with user name '" + userName + "' not found"));
        return mapper.userMapper(userEntity);
    }

    public List<UserDTO> getAllUsersByIdAsc() {
        List<UserEntity> userEntities = userRepository.findAllByOrderByIdAsc();
        if (userEntities.isEmpty()) {
            return Collections.emptyList();
        }

        return userEntities.stream()
                .map(mapper::userMapper)
                .collect(Collectors.toList());
    }


}
