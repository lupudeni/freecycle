package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.AuthenticationEntity;
import com.denisalupu.freecycle.domain.model.AuthenticationDTO;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.AuthenticationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.message.AuthException;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;

    private final Mapper mapper;
    public boolean isUserNameUnique(String userName) {
       return authenticationRepository.findByUserName(userName).isEmpty();
    }

    @Transactional
    public AuthenticationEntity create(AuthenticationDTO authenticationDTO) {
        AuthenticationEntity authenticationEntity = mapper.map(authenticationDTO, AuthenticationEntity.class);
        return authenticationRepository.save(authenticationEntity);

    }
}
