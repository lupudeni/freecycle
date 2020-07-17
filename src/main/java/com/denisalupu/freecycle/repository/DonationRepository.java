package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.entity.AreaOfAvailabilityEntity;
import com.denisalupu.freecycle.domain.entity.CategoryEntity;
import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<DonationEntity, Long> {

    List<DonationEntity> findAllByStatusIn(List<Status> status);

    List<DonationEntity> findAllByStatusAndCategoryAndAreaAndTitleContains(Status status, CategoryEntity category, AreaOfAvailabilityEntity area, String titleKeyWord);

    //TODO: sort by recently posted -> ulala, add timestamps

}
