package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.TestUtil;
import com.denisalupu.freecycle.domain.entity.AuthenticationEntity;
import com.denisalupu.freecycle.domain.model.AuthenticationDTO;
import com.denisalupu.freecycle.domain.model.AuthenticationDetails;
import com.denisalupu.freecycle.exception.UnauthorisedException;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.AuthenticationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationRepository authenticationRepository;

    @Mock
    private Mapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService sut;

    private final TestUtil testUtil = new TestUtil();

    private AuthenticationDTO authenticationDTO;

    private AuthenticationEntity authenticationEntity;

    @BeforeEach
    void setUp() {
        authenticationDTO = testUtil.getAuthenticationDTO();
        authenticationEntity = testUtil.getAuthenticationEntity();
    }

    @Test
    void given_dto_when_create_then_return_entity() {
        //given
        var authDTOMock = mock(AuthenticationDTO.class);
        var authEntityMock = mock(AuthenticationEntity.class);

        when(mapper.map(authDTOMock, AuthenticationEntity.class)).thenReturn(authEntityMock);
        when(authenticationRepository.save(authEntityMock)).thenReturn(authenticationEntity);

        //when
        AuthenticationEntity returnedAuth = sut.create(authDTOMock);

        //then
        assertThat(returnedAuth).isSameAs(authenticationEntity);
    }


    @Test
    void given_wrong_userName_when_loadUserByUsername_then_throw_exception() {
        String username = "john.doe";
        when(authenticationRepository.findByUserName(username))
                .thenReturn(Optional.empty());
        //then
        assertThrows(UnauthorisedException.class, () -> sut.loadUserByUsername(username));
    }

    @Test
    void given_username_and_no_other_matches_when_isUserNameUnique_then_return_true() {
        String username = "john.doe";
        when(authenticationRepository.findByUserName(username))
                .thenReturn(Optional.empty());

        //when
        boolean isUserNameUnique = sut.isUserNameUnique(username);

        //then
        assertThat(isUserNameUnique).isTrue();
    }
}