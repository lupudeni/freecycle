package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.entity.AreaOfAvailabilityEntity;
import com.denisalupu.freecycle.domain.entity.DonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AreaOfAvailabilityRepository extends JpaRepository<AreaOfAvailabilityEntity, Long> {

    Optional<AreaOfAvailabilityEntity> findById(long id);
}
