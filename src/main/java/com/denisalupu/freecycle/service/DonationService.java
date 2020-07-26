package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.Status;
import com.denisalupu.freecycle.domain.entity.*;
import com.denisalupu.freecycle.domain.model.DonationDTO;
import com.denisalupu.freecycle.domain.model.RequestDTO;
import com.denisalupu.freecycle.exception.BadRequestException;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.exception.ForbiddenException;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.DonationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
    public DonationDTO create(DonationDTO donationDTO, UserDetails loggedInUser) {
        donationDTO.setStatus(Status.AVAILABLE);
        DonationEntity donationEntity = mapper.map(donationDTO, DonationEntity.class);
        UserEntity userEntity = userService.findEntityByUserName(loggedInUser.getUsername());
        donationEntity.setDonor(userEntity);
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

    public List<DonationDTO> findAllByStatus(Status[] statuses) {
        List<DonationEntity> donationEntities = donationRepository.findAllByStatusIn(List.of(statuses));

        return mapper.mapCollectionToList(donationEntities, DonationDTO.class);
    }

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
        if(existingDonationEntity.getDonor().getId().equals(request.getUserId())) {
            throw new BadRequestException("Owners cannot request their own donations");
        }
        Set<UserEntity> userRequests = existingDonationEntity.getUserRequests();
        if (userRequests.size() >= MAX_USER_REQUESTS_PER_DONATION) {
            throw new BadRequestException("This donation cannot receive any more requests");
        }
        UserEntity user = userService.findEntityById(request.getUserId());
        userRequests.add(user);
        if (userRequests.size() == 5) {
            existingDonationEntity.setStatus(Status.FULLY_REQUESTED);
            //todo send email to user with the fact that it is fully requested
        }
        return mapper.map(existingDonationEntity, DonationDTO.class);
    }

    @Transactional
    public void abandonRequest(RequestDTO requestDTO) {
        DonationEntity existingDonationEntity = findEntityById(requestDTO.getDonationId());
        Set<UserEntity> userRequest = existingDonationEntity.getUserRequests();
        UserEntity userEntity = userService.findEntityById(requestDTO.getUserId());
        if (!userRequest.contains(userEntity)) {
            throw new BadRequestException("User has not requested the donation yet");
        }
        userRequest.remove(userEntity);
        if (existingDonationEntity.getStatus() == Status.FULLY_REQUESTED) {
            existingDonationEntity.setStatus(Status.AVAILABLE);
        }
    }

    @Transactional
    public DonationDTO update(DonationDTO donationDTO, UserDetails loggedInUser) {
        DonationEntity existingDonationEntity = findEntityById(donationDTO.getId());
        if (checkOwnership(loggedInUser, existingDonationEntity)) {
            return updateFields(existingDonationEntity, donationDTO);
        }
        throw new ForbiddenException("Access denied!");
    }

    private DonationDTO updateFields(DonationEntity existingDonationEntity, DonationDTO donationDTO) {
        existingDonationEntity.setCategory(mapper.map(donationDTO.getCategory(), CategoryEntity.class));
        existingDonationEntity.setTitle(donationDTO.getTitle());
        existingDonationEntity.setDescription(donationDTO.getDescription());
        existingDonationEntity.setArea(mapper.map(donationDTO.getArea(), AreaOfAvailabilityEntity.class));
        existingDonationEntity.setStatus(donationDTO.getStatus());
        return mapper.map(existingDonationEntity, DonationDTO.class);
    }

    @Transactional
    public void giveDonation(long receiverId, long donationId, UserDetails loggedInUser) {
        DonationEntity donationEntity = findEntityById(donationId);
        if (checkOwnership(loggedInUser, donationEntity)) {
            donationEntity.setStatus(Status.DONATED);
            UserEntity receiverEntity = userService.findEntityById(receiverId);
            List<TransactionEntity> transactions = receiverEntity.getTransactions();
            TransactionEntity transactionEntity = TransactionEntity.builder()
                    .donationId(donationId)
                    .receiver(receiverEntity)
                    .build();
            transactions.add(transactionEntity);
            //todo send email to receiver with the phone number of the donor
        }
        throw new ForbiddenException("Access denied!");
    }

    public boolean checkOwnership(UserDetails loggedInUser, DonationEntity existingDonationEntity) {
        UserEntity loggedEntity = userService.findEntityByUserName(loggedInUser.getUsername());
        return loggedEntity.equals(existingDonationEntity.getDonor());
    }


}
