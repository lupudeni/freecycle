package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(long id);

    Optional<UserEntity> findByUserName(String userName);

    List<UserEntity> findAllByOrderByIdAsc();

    List<UserEntity> findAllByOrderByIdDesc();

    //TODO: order all by nr of donated obj asc
    //TODO: order all by nr of donated obj desc

    void deleteById(long id);

}
