package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.entity.AreaOfAvailabilityEntity;
import com.denisalupu.freecycle.domain.entity.CategoryEntity;
import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<DonationEntity, Long> {
    //show all->already there

    Optional<DonationEntity> findById(long id);

    //show all ordered by id asc
    List<DonationEntity> findAllByOrderByIdAsc();

    //show all ordered by id desc
    List<DonationEntity> findAllByOrderByIdDesc();

    //show only donations of a certain status

    List<DonationEntity> findAllByStatusIn(List<Status> status);

    //find all in a certain area//useless

    List<DonationEntity> findAllByArea(AreaOfAvailabilityEntity area);

    //find all by title keyword

    List<DonationEntity> findAllByTitleIsLike(String titleKeyWord);

    List<DonationEntity> findAllByStatusAndCategoryAndAreaAndTitleContains(Status status, CategoryEntity category, AreaOfAvailabilityEntity area, String titleKeyWord);

    //TODO: sort by recently posted -> ulala, add timestamps

    //find by donor //TODO this
    List<DonationEntity> findAllByDonor(UserEntity donor);

    //delete
    void deleteById(long id);

}
