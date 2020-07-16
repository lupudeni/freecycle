package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.domain.model.AreaOfAvailabilityDTO;
import com.denisalupu.freecycle.domain.model.CategoryDTO;
import com.denisalupu.freecycle.domain.model.DonationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.mapper.DTOToEntityMapper;
import com.denisalupu.freecycle.mapper.EntityToDTOMapper;
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
    private final DonationRepository donationRepository;
    private final DTOToEntityMapper dtoToEntityMapper;
    private final EntityToDTOMapper entityToDTOMapper;

    @Transactional
    public DonationDTO create(DonationDTO donationDTO) {
        DonationEntity donationEntity = dtoToEntityMapper.donationMapper(donationDTO);
        DonationEntity savedDonationEntity = donationRepository.save(donationEntity);
        return entityToDTOMapper.donationMapper(savedDonationEntity);
    }

    //this is useless
    public DonationDTO findById(long id) {
        Optional<DonationEntity> optionalDonationEntity = donationRepository.findById(id);
        DonationEntity donationEntity = optionalDonationEntity.orElseThrow(
                () -> new EntityNotFoundException("Donation with id '" + id + "' not found"));
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
//TODO: something here gives -> org.hibernate.PersistentObjectException: detached entity passed to persist.
// This doesn't work. ceva idee de pe stack overflow ar spune ca e din cauza ca eu ii dau un obiect cu idul
// gata definit si el incearca sa il genereze peste
    @Transactional
    public DonationDTO requestDonation(UserDTO userDTO, DonationDTO donationDTO) {
        Set<UserDTO> userRequests = donationDTO.getUserRequests();
        if (userRequests.size() < 5) {
            userRequests.add(userDTO);
        }
        donationDTO.setUserRequests(userRequests);
       return update(donationDTO);
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
    public DonationDTO update(DonationDTO donationDTO) {
        DonationEntity existingDonationEntity = donationRepository.findById(donationDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Donation not found"));
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
