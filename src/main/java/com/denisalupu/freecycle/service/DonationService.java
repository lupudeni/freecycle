package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.Status;
import com.denisalupu.freecycle.domain.entity.*;
import com.denisalupu.freecycle.domain.model.DonationDTO;
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

    private final EmailService emailService;

    private final MessageService messageService;

    private final TransactionService transactionService;

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

    public List<DonationDTO> findDonations(long categoryId, long areaId, String title) {
        CategoryEntity categoryEntity = categoryService.geEntityById(categoryId);
        AreaOfAvailabilityEntity areaEntity = areaService.getEntityById(areaId);

        List<DonationEntity> donationEntities = donationRepository
                .findAllByStatusAndCategoryAndAreaAndTitleContains(Status.AVAILABLE, categoryEntity, areaEntity, title);

        return mapper.mapCollectionToList(donationEntities, DonationDTO.class);
    }

    /**
     * Gets all donations with status "AVAILABLE" and "FULLY_REQUESTED" posted by the logged in user
     *
     * @param loggedInUser currently logged in User
     * @return List of active donations of current user
     */
    public List<DonationDTO> getAllActiveDonations(UserDetails loggedInUser) {
        UserEntity userEntity = userService.findEntityByUserName(loggedInUser.getUsername());
        List<DonationEntity> donationEntities = donationRepository.findAllByDonorAndStatusIn(userEntity, List.of(Status.AVAILABLE, Status.FULLY_REQUESTED));
        return mapper.mapCollectionToList(donationEntities, DonationDTO.class);
    }

    /**
     * Gets all donations with status "DONATED" posted by the logged in user
     *
     * @param loggedInUser currently logged in user
     * @return List of past donations of current user
     */
    public List<DonationDTO> getAllHistory(UserDetails loggedInUser) {
        UserEntity userEntity = userService.findEntityByUserName(loggedInUser.getUsername());
        List<DonationEntity> donationEntities = donationRepository.findAllByDonorAndStatus(userEntity, Status.DONATED);
        return mapper.mapCollectionToList(donationEntities, DonationDTO.class);
    }

    /**
     * Gets all donations requested by the logged in user
     *
     * @param loggedInUser currently logged in user
     * @return List of requested donations
     */
    public List<DonationDTO> getAllRequests(UserDetails loggedInUser) {
        UserEntity userEntity = userService.findEntityByUserName(loggedInUser.getUsername());
        Set<DonationEntity> donationEntities = userEntity.getRequestedDonations();
        return mapper.mapCollectionToList(donationEntities, DonationDTO.class);
    }


    @Transactional
    public DonationDTO requestDonation(long donationId, UserDetails loggedInUser) {
        DonationEntity existingDonationEntity = findEntityById(donationId);
        UserEntity requesterEntity = userService.findEntityByUserName(loggedInUser.getUsername());
        if (existingDonationEntity.getDonor().getId().equals(requesterEntity.getId())) {
            throw new BadRequestException("Owners cannot request their own donations");
        }
        Set<UserEntity> userRequests = existingDonationEntity.getUserRequests();
        if (userRequests.size() >= MAX_USER_REQUESTS_PER_DONATION) {
            throw new BadRequestException("This donation cannot receive any more requests");
        }
        userRequests.add(requesterEntity);
        if (userRequests.size() == 5) {
            existingDonationEntity.setStatus(Status.FULLY_REQUESTED);
            sendFullyRequestedEmail(existingDonationEntity);
        }
        return mapper.map(existingDonationEntity, DonationDTO.class);
    }

    public void sendFullyRequestedEmail(DonationEntity existingDonationEntity) {
        String title = existingDonationEntity.getTitle();
        String donorEmail = existingDonationEntity.getDonor().getEmail();
        String message = messageService.getMessage("fully.requested.email", List.of(title));
        emailService.sendEmail(donorEmail, "Your donation is fully requested!", message);
    }

    @Transactional
    public void abandonRequest(long donationId, UserDetails loggedInUser) {
        DonationEntity existingDonationEntity = findEntityById(donationId);
        Set<UserEntity> userRequest = existingDonationEntity.getUserRequests();
        UserEntity userEntity = userService.findEntityByUserName(loggedInUser.getUsername());
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
        if (!checkOwnership(loggedInUser, donationEntity)) {
            throw new ForbiddenException("Access denied!");
        }
        if (donationEntity.getStatus() == Status.DONATED) {
            throw new BadRequestException("Donation no longer available");
        }
        donationEntity.setStatus(Status.DONATED);
        UserEntity receiverEntity = userService.findEntityById(receiverId);
        transactionService.save(donationId, receiverEntity);

        sendGiveDonationEmail(loggedInUser, donationEntity, receiverEntity);
    }

    public void sendGiveDonationEmail(UserDetails loggedInUser, DonationEntity donationEntity, UserEntity receiverEntity) {
        String receiverEmail = receiverEntity.getEmail();
        String title = donationEntity.getTitle();
        UserEntity donorEntity = userService.findEntityByUserName(loggedInUser.getUsername());
        String phoneNumber = donorEntity.getPhone();
        String message = messageService.getMessage("give.donation", List.of(title, phoneNumber));
        emailService.sendEmail(receiverEmail, "You received a donation!", message);
    }

    public boolean checkOwnership(UserDetails loggedInUser, DonationEntity existingDonationEntity) {
        UserEntity loggedEntity = userService.findEntityByUserName(loggedInUser.getUsername());
        return loggedEntity.equals(existingDonationEntity.getDonor());
    }


}
