package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(long id);

    Optional<UserEntity> findByUserName(String userName);

    List<UserEntity> findAllByOrderByIdAsc();

    List<UserEntity> findAllByOrderByIdDesc();

//    List<UserEntity> findAllByOrderByDonatedObjectsAsc();
//
//    List<UserEntity> findAllByOrderByDonatedObjectsDesc();

    void deleteById(long id);

}
