package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.entity.AuthenticationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByAuthentication(AuthenticationEntity authenticationEntity);

    @Query("select u from UserEntity u where u.authentication.userName = :userName")
    Optional<UserEntity> findByUserName(String userName);
}
