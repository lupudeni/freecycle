package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.entity.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, Long> {

    Optional<AuthenticationEntity> findByUserName(String userName);
}
