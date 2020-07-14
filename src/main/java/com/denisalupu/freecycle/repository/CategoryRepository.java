package com.denisalupu.freecycle.repository;

import com.denisalupu.freecycle.domain.entity.CategoryEntity;
import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.utils.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    //find by id
    Optional<CategoryEntity> findById(long id);

    //find by category name
    Optional<CategoryEntity> findByCategoryName(CategoryName category);

    //find by donation
    Optional<CategoryEntity> findByDonationsContains(DonationEntity donation);

    //show all ordered by id
    List<CategoryEntity> findAllByOrderById();

    //show all ordered by category name
    List<CategoryEntity> findAllByOrderByCategoryName();

    //TODO: show all ordered by donations number

    //delete
    void deleteById(long id);

}
