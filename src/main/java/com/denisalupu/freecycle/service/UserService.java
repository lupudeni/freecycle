package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.AuthenticationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.AuthenticationDTO;
import com.denisalupu.freecycle.domain.model.RegistrationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//todo //add tables for pass word and roles
@Service
@AllArgsConstructor
public class UserService {

    private final Mapper mapper;

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    @Transactional
    public UserDTO registerNewUser(RegistrationDTO registrationDTO) {
        AuthenticationDTO authenticationDTO = AuthenticationDTO.builder()
                .userName(registrationDTO.getUserName())
                .password(registrationDTO.getPassword())
                .build();
        AuthenticationEntity authenticationEntity = authenticationService.create(authenticationDTO);
        UserEntity userEntity = mapper.map(registrationDTO, UserEntity.class);
        userEntity.setAuthentication(authenticationEntity);
        UserEntity savedEntity = userRepository.save(userEntity);
        return mapper.map(savedEntity, UserDTO.class);
    }

    public boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    //TODO: create a change password method that updates only the password field

    @Transactional
    public UserDTO update(UserDTO userDTO) {
        UserEntity existingUserEntity = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return updateFields(existingUserEntity, userDTO);
    }

    private UserDTO updateFields(UserEntity existingUserEntity, UserDTO userDTO) {
        existingUserEntity.setFirstName(userDTO.getFirstName());
        existingUserEntity.setLastName(userDTO.getLastName());
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

    public UserEntity findEntityByUserName(String userName) {
       return userRepository.findByUserName(userName).orElseThrow( () -> new EntityNotFoundException("Incorrect login credentials"));
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
