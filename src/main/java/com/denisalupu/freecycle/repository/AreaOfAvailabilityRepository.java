package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.entity.AreaOfAvailabilityEntity;
import com.denisalupu.freecycle.domain.entity.DonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AreaOfAvailabilityRepository extends JpaRepository<AreaOfAvailabilityEntity, Long> {

    //find by id
    Optional<AreaOfAvailabilityEntity> findById(long id);

    //find by a donation
    Optional<AreaOfAvailabilityEntity> findByDonationsContains(DonationEntity donation);

    //show all ordered by id
    List<AreaOfAvailabilityEntity> findAllByOrderByIdAsc();

    //show all ordered by country
    List<AreaOfAvailabilityEntity> findAllByOrderByCountry();

    //show all ordered by id desc
    List<AreaOfAvailabilityEntity> findAllByOrderByIdDesc();

    //find all by country
    List<AreaOfAvailabilityEntity> findAllByCountry(String country);

    //find all by city
    List<AreaOfAvailabilityEntity> findAllByCity(String city);

    //delete by id
    void deleteById(long id);
}
