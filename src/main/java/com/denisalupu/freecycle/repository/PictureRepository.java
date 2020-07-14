package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

    //find by id
    Optional<PictureEntity> findById(long id);

    //find all by donation
    List<PictureEntity> findAllByDonation(DonationEntity donation);

    //delete
    void deleteById(long id);
}
