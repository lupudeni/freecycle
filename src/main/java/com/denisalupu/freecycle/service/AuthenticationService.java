package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.AuthenticationEntity;
import com.denisalupu.freecycle.domain.model.AuthenticationDTO;
import com.denisalupu.freecycle.domain.model.AuthenticationDetails;
import com.denisalupu.freecycle.exception.UnauthorisedException;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.AuthenticationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.message.AuthException;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final AuthenticationRepository authenticationRepository;

    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    public boolean isUserNameUnique(String userName) {
        return authenticationRepository.findByUserName(userName).isEmpty();
    }

    @Transactional
    public AuthenticationEntity create(AuthenticationDTO authenticationDTO) {
        authenticationDTO.setPassword(passwordEncoder.encode(authenticationDTO.getPassword()));
        AuthenticationEntity authenticationEntity = mapper.map(authenticationDTO, AuthenticationEntity.class);
        return authenticationRepository.save(authenticationEntity);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthenticationEntity authenticationEntity = authenticationRepository.findByUserName(username)
                .orElseThrow(() -> new UnauthorisedException("Login failed"));

        AuthenticationDTO authenticationDTO = mapper.map(authenticationEntity, AuthenticationDTO.class);
        return new AuthenticationDetails(authenticationDTO);
    }


}
