package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.*;
import com.denisalupu.freecycle.exception.BadRequestException;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.mapper.DTOToEntityMapper;
import com.denisalupu.freecycle.mapper.EntityToDTOMapper;
import com.denisalupu.freecycle.repository.DonationRepository;
import com.denisalupu.freecycle.utils.SortOrder;
import com.denisalupu.freecycle.utils.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DonationService {

    public static final int MAX_USER_REQUESTS_PER_DONATION = 5;

    private final UserService userService;
    private final DonationRepository donationRepository;
    private final DTOToEntityMapper dtoToEntityMapper;
    private final EntityToDTOMapper entityToDTOMapper;

    @Transactional
    public DonationDTO create(DonationDTO donationDTO) {
        DonationEntity donationEntity = dtoToEntityMapper.donationMapper(donationDTO);
        DonationEntity savedDonationEntity = donationRepository.save(donationEntity);
        return entityToDTOMapper.donationMapper(savedDonationEntity);
    }


    private DonationEntity findEntityById(long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Donation with id '" + id + "' not found"));
    }

    //this is useless
    public DonationDTO findById(long id) {
        DonationEntity donationEntity = findEntityById(id);
        return entityToDTOMapper.donationMapper(donationEntity);
    }

    //this is useless
    public List<DonationDTO> getAllOrderedById(SortOrder sortOrder) {
        List<DonationEntity> donationEntities;
        if (sortOrder == SortOrder.ASC) {
            donationEntities = donationRepository.findAllByOrderByIdAsc();
        } else {
            donationEntities = donationRepository.findAllByOrderByIdDesc();
        }
        return donationEntities.stream()
                .map(entityToDTOMapper::donationMapper)
                .collect(Collectors.toList());
    }

    public List<DonationDTO> findAllByStatus(Status[] statuses) {
        List<DonationEntity> donationEntities = donationRepository.findAllByStatusIn(List.of(statuses));

        return donationEntities.stream()
                .map(entityToDTOMapper::donationMapper)
                .collect(Collectors.toList());
    }

    public List<DonationDTO> findDonations(CategoryDTO categoryDTO, AreaOfAvailabilityDTO areaDTO, String title) {
        List<DonationEntity> donationEntities = donationRepository
                .findAllByStatusAndCategoryAndAreaAndTitleContains(Status.AVAILABLE,
                        dtoToEntityMapper.categoryMapper(categoryDTO),
                        dtoToEntityMapper.areaMapper(areaDTO), title);
        return donationEntities.stream()
                .map(entityToDTOMapper::donationMapper)
                .collect(Collectors.toList());

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
        return entityToDTOMapper.donationMapper(existingDonationEntity);

    }

    @Transactional
    public DonationDTO update(DonationDTO donationDTO) {
        DonationEntity existingDonationEntity = findEntityById(donationDTO.getId());
        return updateFields(existingDonationEntity, donationDTO);
    }

    private DonationDTO updateFields(DonationEntity existingDonationEntity, DonationDTO donationDTO) {
        existingDonationEntity.setCategory(dtoToEntityMapper.categoryMapper(donationDTO.getCategory()));
        existingDonationEntity.setTitle(donationDTO.getTitle());
        existingDonationEntity.setDescription(donationDTO.getDescription());
        existingDonationEntity.setArea(dtoToEntityMapper.areaMapper(donationDTO.getArea()));
        existingDonationEntity.setStatus(donationDTO.getStatus());
       UserDTO receiver = donationDTO.getReceiver();
       if (receiver != null) {
           existingDonationEntity.setReceiver(dtoToEntityMapper.userMapper(receiver));
       }
       Set<UserEntity> userRequests = donationDTO.getUserRequests().stream()
               .map(dtoToEntityMapper::userMapper)
               .collect(Collectors.toSet());
        existingDonationEntity.setUserRequests(userRequests);

        return entityToDTOMapper.donationMapper(existingDonationEntity);
    }


}
