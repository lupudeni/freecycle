package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.AuthenticationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.RegistrationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.exception.ForbiddenException;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private Mapper mapper;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService sut;

    private final UserServiceTestUtil userServiceTestUtil = new UserServiceTestUtil();


    private UserDTO userDTO;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userDTO = userServiceTestUtil.getUserDTO();
        userEntity = userServiceTestUtil.getUserEntity();
    }

    @Test
    void given_registrationDTO_when_create_new_user_then_return_new_userDTO() {
        //given
        RegistrationDTO registrationDTOMock = mock(RegistrationDTO.class);
        AuthenticationEntity authenticationEntityMock = mock(AuthenticationEntity.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        when(authenticationService.create(any()))
                .thenReturn(authenticationEntityMock);

        when(mapper.map(registrationDTOMock, UserEntity.class))
                .thenReturn(userEntityMock);
        UserEntity savedUserEntityMock = mock(UserEntity.class);
        when(userRepository.save(userEntityMock)).thenReturn(savedUserEntityMock);
        when(mapper.map(savedUserEntityMock, UserDTO.class))
                .thenReturn(userDTO);
        //when
        UserDTO actualUserDTO = sut.registerNewUser(registrationDTOMock);

        //then
        assertThat(actualUserDTO).isSameAs(userDTO);
    }

    @Test
    void given_existing_id_when_findEntityBYId_then_return_user_entity() {
        //given
        long id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        //when
        UserEntity actualEntity = sut.findEntityById(id);

        //then
        assertThat(actualEntity).isSameAs(userEntity);
    }

    @Test
    void given_non_existent_id_when_findEntityBYId_then_throw_exception() {
        //given
        long id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> sut.findEntityById(id));
    }

    @Test
    void given_existing_user_name_when_findEntityByUserName_then_return_user_entity() {
        //given
        String userName = "john.doe";
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));

        //when
        UserEntity actualUserEntity = sut.findEntityByUserName(userName);

        //then
        assertThat(actualUserEntity).isSameAs(userEntity);
    }

    @Test
    void given_non_existent_userName_when_findEntityByUserName_then_throw_exception() {
        //given
        String userName = "non.existent";
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> sut.findEntityByUserName(userName));
    }

    @Test
    void given_userName_when_findUserByUserName_the_return_userDTO() {
        String userName = "john.doe";
        UserDetails loggedInUserMock = mock(UserDetails.class);
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(userRepository.findByUserName(loggedInUserMock.getUsername())).thenReturn(Optional.of(userEntity));
        when(mapper.map(userEntity, UserDTO.class)).thenReturn(userDTO);

        //when
        UserDTO actualUserDTO = sut.findUserByUserName(userName, loggedInUserMock);

        //then
        assertThat(actualUserDTO).isSameAs(userDTO);
    }

    @Test
    void given_not_authentified_user_when_findUserByUserName_then_throw_exception() {
        String userName = "john.doe";
        UserDetails loggedInUserMock = mock(UserDetails.class);
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        UserEntity anotherUser = mock(UserEntity.class);
        when(userRepository.findByUserName(loggedInUserMock.getUsername())).thenReturn(Optional.of(anotherUser));

        //then
        assertThrows(ForbiddenException.class, () -> sut.findUserByUserName(userName, loggedInUserMock));
    }

    @Test
    void given_id_and_correct_user_when_deleteById_then_delete_user() {
        long id = 1L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        when(userRepository.findByUserName(loggedInUserMock.getUsername())).thenReturn(Optional.of(userEntity));
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        //when
        sut.deleteById(id, loggedInUserMock);

        //then
        verify(userRepository).deleteById(id);
    }

    @Test
    void given_id_and_incorrect_user_when_deleteById_then_throw_exception() {
        long id = 1L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity anotherUser = mock(UserEntity.class);

        when(userRepository.findByUserName(loggedInUserMock.getUsername())).thenReturn(Optional.of(anotherUser));
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        //then
        assertThrows(ForbiddenException.class, () -> sut.deleteById(id, loggedInUserMock));
    }

    @Test
    void given_userDTO_and_correct_user_when_update_then_return_updated_user() {
        //given
        userDTO.setFirstName("John-John");
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(userEntity));
        UserDetails loggedInUserMock = mock(UserDetails.class);
        when(userRepository.findByUserName(loggedInUserMock.getUsername())).thenReturn(Optional.of(userEntity));

        when(mapper.map(userEntity, UserDTO.class)).thenReturn(userDTO);

        //when
        UserDTO actualUserDTO = sut.update(userDTO, loggedInUserMock);

        //then
        assertThat(actualUserDTO).isSameAs(userDTO);
        assertThat(userEntity.getFirstName()).isEqualTo(userDTO.getFirstName());
    }

    @Test
    void given_non_existent_userDTO_when_update_then_throw_exception() {
        //given
        UserDTO userDTOMock = mock(UserDTO.class);
        when(userRepository.findById(userDTOMock.getId())).thenReturn(Optional.empty());
        UserDetails loggedInUserMock = mock(UserDetails.class);

        //then
        assertThrows(EntityNotFoundException.class, () -> sut.update(userDTOMock, loggedInUserMock));
    }

    @Test
    void given_userDTO_and_incorrect_user_when_update_then_throw_exception() {
        //given
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(userEntity));
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity anotherUserMock = mock(UserEntity.class);
        when(userRepository.findByUserName(loggedInUserMock.getUsername())).thenReturn(Optional.of(anotherUserMock));

        //then
        assertThrows(ForbiddenException.class, () -> sut.update(userDTO, loggedInUserMock));
    }
}