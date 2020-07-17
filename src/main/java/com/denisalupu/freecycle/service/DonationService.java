package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.AreaOfAvailabilityEntity;
import com.denisalupu.freecycle.domain.entity.CategoryEntity;
import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.*;
import com.denisalupu.freecycle.exception.BadRequestException;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.DonationRepository;
import com.denisalupu.freecycle.utils.SortOrder;
import com.denisalupu.freecycle.utils.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DonationService {

    public static final int MAX_USER_REQUESTS_PER_DONATION = 5;

    private final UserService userService;
    private final CategoryService categoryService;
    private final AreaOfAvailabilityService areaService;
    private final DonationRepository donationRepository;
    private final Mapper mapper;

    @Transactional
    public DonationDTO create(DonationDTO donationDTO) {
        DonationEntity donationEntity = mapper.map(donationDTO, DonationEntity.class);
        DonationEntity savedDonationEntity = donationRepository.save(donationEntity);
        return mapper.map(savedDonationEntity, DonationDTO.class);
    }


    public DonationEntity findEntityById(long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Donation with id '" + id + "' not found"));
    }

    //this is useless
    public DonationDTO findById(long id) {
        DonationEntity donationEntity = findEntityById(id);
        return mapper.map(donationEntity, DonationDTO.class);
    }


    //this is useless
    public List<DonationDTO> getAllOrderedById(SortOrder sortOrder) {
        List<DonationEntity> donationEntities;
        if (sortOrder == SortOrder.ASC) {
            donationEntities = donationRepository.findAllByOrderByIdAsc();
        } else {
            donationEntities = donationRepository.findAllByOrderByIdDesc();
        }
        return mapper.mapCollectionToList(donationEntities, DonationDTO.class);
    }

    public List<DonationDTO> findAllByStatus(Status[] statuses) {
        List<DonationEntity> donationEntities = donationRepository.findAllByStatusIn(List.of(statuses));

        return mapper.mapCollectionToList(donationEntities, DonationDTO.class);
    }
//old
//    public List<DonationDTO> findDonations(CategoryDTO categoryDTO, AreaOfAvailabilityDTO areaDTO, String title) {
//        List<DonationEntity> donationEntities = donationRepository
//                .findAllByStatusAndCategoryAndAreaAndTitleContains(Status.AVAILABLE,
//                        mapper.map(categoryDTO, CategoryEntity.class),
//                        mapper.map(areaDTO, AreaOfAvailabilityEntity.class), title);
//
//        return mapper.mapCollectionToList(donationEntities, DonationDTO.class);
//
//    }

    public List<DonationDTO> findDonations(long categoryId, long areaId, String title) {
        CategoryEntity categoryEntity = categoryService.geEntityById(categoryId);
        AreaOfAvailabilityEntity areaEntity = areaService.getEntityById(areaId);

        List<DonationEntity> donationEntities = donationRepository
                .findAllByStatusAndCategoryAndAreaAndTitleContains(Status.AVAILABLE, categoryEntity, areaEntity, title);

        return mapper.mapCollectionToList(donationEntities, DonationDTO.class);

    }


    @Transactional
    public DonationDTO requestDonation(RequestDTO request) {
        DonationEntity existingDonationEntity = findEntityById(request.getDonationId());
        Set<UserEntity> userRequests = existingDonationEntity.getUserRequests();
        if (userRequests.size() >= MAX_USER_REQUESTS_PER_DONATION) {
            throw new BadRequestException("This donation cannot receive any more requests");
        }

        UserEntity user = userService.findEntityById(request.getUserId());
        userRequests.add(user);
        return mapper.map(existingDonationEntity, DonationDTO.class);
    }

    @Transactional
    public void abandonRequest(RequestDTO requestDTO) {
        DonationEntity existingDonationEntity = findEntityById(requestDTO.getDonationId());
        Set<UserEntity> userRequest = existingDonationEntity.getUserRequests();
        UserEntity userEntity = userService.findEntityById(requestDTO.getUserId());
        if (userRequest.contains(userEntity)) {
            userRequest.remove(userEntity);
        } else {
            throw new EntityNotFoundException("User has not requested the donation yet");
        }
    }

    @Transactional
    public DonationDTO update(DonationDTO donationDTO) {
        DonationEntity existingDonationEntity = findEntityById(donationDTO.getId());
        return updateFields(existingDonationEntity, donationDTO);
    }

    private DonationDTO updateFields(DonationEntity existingDonationEntity, DonationDTO donationDTO) {
        existingDonationEntity.setCategory(mapper.map(donationDTO.getCategory(), CategoryEntity.class));
        existingDonationEntity.setTitle(donationDTO.getTitle());
        existingDonationEntity.setDescription(donationDTO.getDescription());
        existingDonationEntity.setArea(mapper.map(donationDTO.getArea(), AreaOfAvailabilityEntity.class));
        existingDonationEntity.setStatus(donationDTO.getStatus());
        UserDTO receiver = donationDTO.getReceiver();
        if (receiver != null) {
            existingDonationEntity.setReceiver(mapper.map(receiver, UserEntity.class));
        }
        Set<UserEntity> userRequests = mapper.mapCollectionToSet(donationDTO.getUserRequests(), UserEntity.class);
        existingDonationEntity.setUserRequests(userRequests);
        return mapper.map(existingDonationEntity, DonationDTO.class);
    }


}
