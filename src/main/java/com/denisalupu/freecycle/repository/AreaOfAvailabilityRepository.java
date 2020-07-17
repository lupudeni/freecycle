package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.entity.AreaOfAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaOfAvailabilityRepository extends JpaRepository<AreaOfAvailabilityEntity, Long> {

}
