package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.Status;
import com.denisalupu.freecycle.domain.entity.AreaOfAvailabilityEntity;
import com.denisalupu.freecycle.domain.entity.CategoryEntity;
import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<DonationEntity, Long> {

    List<DonationEntity> findAllByStatusIn(List<Status> status);

    List<DonationEntity> findAllByStatusAndCategoryAndAreaAndTitleContains(Status status, CategoryEntity category, AreaOfAvailabilityEntity area, String titleKeyWord);

    List<DonationEntity> findAllByDonorAndStatusIn(UserEntity donor, List<Status> status);


    List<DonationEntity> findAllByDonorAndStatus(UserEntity donor, Status status);

    //TODO: sort by recently posted -> ulala, add timestamps

}
