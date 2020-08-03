package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.TestUtil;
import com.denisalupu.freecycle.domain.Status;
import com.denisalupu.freecycle.domain.entity.*;
import com.denisalupu.freecycle.domain.model.DonationDTO;
import com.denisalupu.freecycle.domain.model.UserDTO;
import com.denisalupu.freecycle.exception.BadRequestException;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.exception.ForbiddenException;
import com.denisalupu.freecycle.mapper.Mapper;
import com.denisalupu.freecycle.repository.DonationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private AreaOfAvailabilityService areaService;

    @Mock
    private EmailService emailService;

    @Mock
    private MessageService messageService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private DonationService sut;

    private final TestUtil testUtil = new TestUtil();

    private DonationEntity donationEntity;

    private UserEntity userEntity;

    private DonationDTO donationDTO;

    private UserDTO userDTO;

    private CategoryEntity categoryEntity;

    private AreaOfAvailabilityEntity areaEntity;

    @BeforeEach
    void setUp() {
        donationEntity = testUtil.getDonationEntity();
        donationDTO = testUtil.getDonationDTO();
        userDTO = testUtil.getUserDTO();
        userEntity = testUtil.getUserEntity();
        categoryEntity = testUtil.getCategoryEntity();
        areaEntity = testUtil.getAreaOfAvailabilityEntity();
    }

    @Test
    void given_donationDTO_and_loggedInUserDetails_when_create_then_donation_is_create_in_db() {
        //given
        DonationDTO donationDTOMock = mock(DonationDTO.class);
        DonationEntity donationEntityMock = mock(DonationEntity.class);
        DonationEntity savedEntityMock = mock(DonationEntity.class);

        when(mapper.map(donationDTOMock, DonationEntity.class)).thenReturn(donationEntityMock);

        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity donorEntityMock = mock(UserEntity.class);

        when(userService.findEntityByUserName(loggedInUserMock.getUsername())).thenReturn(donorEntityMock);
        when(donationRepository.save(donationEntityMock)).thenReturn(savedEntityMock);
        when(mapper.map(savedEntityMock, DonationDTO.class)).thenReturn(donationDTO);

        //When
        DonationDTO actualDonationDTO = sut.create(donationDTOMock, loggedInUserMock);

        //then
        verify(donationRepository).save(donationEntityMock);
        assertThat(actualDonationDTO.getStatus()).isEqualTo(Status.AVAILABLE);
        assertThat(actualDonationDTO).isSameAs(donationDTO);
    }

    @Test
    void given_id_when_findEntityById_then_return_optional_of_entity() {
        //given
        long id = 1;
        DonationEntity donationEntity = this.donationEntity;

        when(donationRepository.findById(id)).thenReturn(Optional.of(donationEntity));

        //when
        DonationEntity returnedDonationEntity = sut.findEntityById(id);

        //then
        verify(donationRepository).findById(id);
        assertThat(returnedDonationEntity).isSameAs(donationEntity);
    }


    @Test
    void given_nonexistent_id_when_findEntityById_then_throw_exception() {
        //given
        long id = 1L;
        when(donationRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> sut.findEntityById(id));
        verify(donationRepository).findById(id);
    }

    @Test
    void given_ids_and_title_when_find_donation_then_return_list_of_donationsDTOs() {
        //given
        long categoryId = 7L;
        long areaId = 1L;
        String title = "Fallout";

        DonationEntity donationEntity = this.donationEntity;
        CategoryEntity categoryEntity = donationEntity.getCategory();
        AreaOfAvailabilityEntity areaEntity = donationEntity.getArea();

        List<DonationEntity> donationEntities = List.of(donationEntity);

        List<DonationDTO> donationDTOS = List.of(donationDTO);
        when(categoryService.geEntityById(categoryId)).thenReturn(categoryEntity);
        when(areaService.getEntityById(areaId)).thenReturn(areaEntity);

        when(donationRepository
                .findAllByStatusAndCategoryAndAreaAndTitleContains(Status.AVAILABLE, categoryEntity, areaEntity, title))
                .thenReturn(donationEntities);

        when(mapper.mapDonationDtos(donationEntities)).thenReturn(donationDTOS);

        //when
        List<DonationDTO> returnedList = sut.findDonations(categoryId, areaId, title);

        //then
        verify(donationRepository).findAllByStatusAndCategoryAndAreaAndTitleContains(Status.AVAILABLE, categoryEntity, areaEntity, title);
        assertThat(returnedList).isEqualTo(donationDTOS);
    }

    @Test
    void given_ids_and_unknown_title_when_find_donation_then_return_empty_list() {
        //given
        long categoryId = 7L;
        long areaId = 1L;
        String title = "Sims";

        DonationEntity donationEntity = this.donationEntity;
        CategoryEntity categoryEntity = donationEntity.getCategory();
        AreaOfAvailabilityEntity areaEntity = donationEntity.getArea();

        List<DonationEntity> donationEntities = Collections.emptyList();

        when(categoryService.geEntityById(categoryId)).thenReturn(categoryEntity);
        when(areaService.getEntityById(areaId)).thenReturn(areaEntity);

        when(donationRepository
                .findAllByStatusAndCategoryAndAreaAndTitleContains(Status.AVAILABLE, categoryEntity, areaEntity, title))
                .thenReturn(donationEntities);

        when(mapper.mapDonationDtos(donationEntities)).thenReturn(Collections.emptyList());

        //when
        List<DonationDTO> returnedList = sut.findDonations(categoryId, areaId, title);

        //then
        verify(donationRepository).findAllByStatusAndCategoryAndAreaAndTitleContains(Status.AVAILABLE, categoryEntity, areaEntity, title);
        assertThat(returnedList).isEqualTo(Collections.emptyList());
    }

    @Test
    void given_user_details_when_findAllActiveDonations_then_return_list_of_Available_DonationDTO() {
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity donorEntityMock = mock(UserEntity.class);
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(donorEntityMock);

        DonationEntity donationEntityMock = mock(DonationEntity.class);
        List<DonationEntity> donationEntitiesMock = List.of(donationEntityMock);

        List<DonationDTO> donationDTOS = List.of(donationDTO);
        when(donationRepository.findAllByDonorAndStatusIn(donorEntityMock, List.of(Status.AVAILABLE, Status.FULLY_REQUESTED)))
                .thenReturn(donationEntitiesMock);

        when(mapper.mapDonationDtos(donationEntitiesMock))
                .thenReturn(donationDTOS);

        //when
        List<DonationDTO> returnedDonations = sut.getAllActiveDonations(loggedInUserMock);

        //then
        verify(donationRepository).findAllByDonorAndStatusIn(donorEntityMock, List.of(Status.AVAILABLE, Status.FULLY_REQUESTED));
        assertThat(returnedDonations).isEqualTo(donationDTOS);
    }

    @Test
    void given_user_details_when_getAllHistory_then_return_list_of_donated_DonationDTO() {
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity donorEntityMock = mock(UserEntity.class);
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(donorEntityMock);

        DonationEntity donationEntityMock = mock(DonationEntity.class);
        List<DonationEntity> donationEntitiesMock = List.of(donationEntityMock);
        when(donationRepository.findAllByDonorAndStatus(donorEntityMock, Status.DONATED))
                .thenReturn(donationEntitiesMock);

//        DonationDTO donationDTO = getDonationDTO();
        donationDTO.setStatus(Status.DONATED);
        List<DonationDTO> donationDTOS = List.of(donationDTO);

        when(mapper.mapDonationDtos(donationEntitiesMock))
                .thenReturn(donationDTOS);

        //when
        List<DonationDTO> returnedDonations = sut.getAllHistory(loggedInUserMock);

        //then
        verify(donationRepository).findAllByDonorAndStatus(donorEntityMock, Status.DONATED);
        assertThat(returnedDonations).isEqualTo(donationDTOS);
    }

    @Test
    void given_user_details_when_getAllRequests_then_return_list_of_requested_donations() {
        //given
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity requesterEntityMock = mock(UserEntity.class);
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(requesterEntityMock);

        DonationEntity donationEntityMock = mock(DonationEntity.class);
        Set<DonationEntity> requestsMock = Set.of(donationEntityMock);
        when(requesterEntityMock.getRequestedDonations())
                .thenReturn(requestsMock);

        List<DonationDTO> donationDTOS = List.of(donationDTO);

        when(mapper.mapCollectionToList(requestsMock, DonationDTO.class))
                .thenReturn(donationDTOS);

        //when
        List<DonationDTO> returnedDonations = sut.getAllRequests(loggedInUserMock);

        //then

        assertThat(returnedDonations).isEqualTo(donationDTOS);
    }

    /**
     * Conditions:
     * - donation id of existing donation
     * - logged in user (requester) is different from donation owner (donor), exemplified here by different ids
     * - the set of requesting users for given donation is not at full capacity
     * Expectations:
     * - The method will add the requesting user to the "userRequests" set of the donation
     * - The method will return the new DonationDTO object containing the modified set of requesting users
     */
    @Test
    void given_donationId_and_user_details_when_requestDonation_then_return_donationDTO() {
        //given
        long donationId = 1L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity requesterEntity = UserEntity.builder().id(2L).build();
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(requesterEntity);

        DonationEntity existingDonationEntity = donationEntity;
        when(donationRepository.findById(donationId))
                .thenReturn(Optional.of(existingDonationEntity));

        donationDTO.setUserRequests(Set.of(userDTO));
        when(mapper.map(existingDonationEntity, DonationDTO.class))
                .thenReturn(donationDTO);

        //when
        DonationDTO actualDonation = sut.requestDonation(donationId, loggedInUserMock);

        //then
        assertThat(actualDonation).isSameAs(donationDTO);

    }

    /**
     * Conditions:
     * - donation id of existing donation
     * - logged in user (requester) is the same as the donation owner (donor), exemplified here as having the same ids
     * Expectations:
     * - The method call will throw a BadRequestException
     */
    @Test
    void given_equal_donor_requester_id_when_requestDonation_then_throw_exception() {
        //given
        long donationId = 1L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity requesterEntity = UserEntity.builder()
                .id(1L)
                .build();
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(requesterEntity);

        DonationEntity existingDonationEntity = donationEntity;
        when(donationRepository.findById(donationId))
                .thenReturn(Optional.of(existingDonationEntity));
        //then
        assertThrows(BadRequestException.class, () -> sut.requestDonation(donationId, loggedInUserMock));
    }

    /**
     * Test for: DonationDTO requestDonation(long donationId, UserDetails loggedInUser)
     * Conditions:
     * - donation id of existing donation
     * - logged in user (requester) is different from donation owner (donor), exemplified here by different ids
     * - the set of requesting users for given donation is at full capacity
     * Expectations:
     * - The method call will throw a BadRequestException
     */
    @Test
    void given_max_capacity_list_when_requestDonation_then_throw_exception() {
        //given
        long donationId = 1L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity requesterEntity = UserEntity.builder().id(2L).build();
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(requesterEntity);

        Set<UserEntity> userRequests = donationEntity.getUserRequests();
        testUtil.add5UserRequests(userRequests);
        when(donationRepository.findById(donationId))
                .thenReturn(Optional.of(donationEntity));

        //then
        assertThrows(BadRequestException.class, () -> sut.requestDonation(donationId, loggedInUserMock));
    }

    @Test
    void given_4_requests_when_requestDonation_then_change_status_and_send_email() {
        //given
        long donationId = 1L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity requesterEntity = UserEntity.builder().id(2L).build();
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(requesterEntity);

        Set<UserEntity> userRequests = donationEntity.getUserRequests();
        testUtil.add4UserRequests(userRequests);

        donationEntity.setUserRequests(userRequests);
        when(donationRepository.findById(donationId))
                .thenReturn(Optional.of(donationEntity));

        donationDTO.setStatus(Status.FULLY_REQUESTED);
        when(mapper.map(donationEntity, DonationDTO.class))
                .thenReturn(donationDTO);
        //when
        DonationDTO actualDonation = sut.requestDonation(donationId, loggedInUserMock);

        //then
        assertThat(actualDonation).isSameAs(donationDTO);
        assertThat(actualDonation.getStatus()).isEqualTo(Status.FULLY_REQUESTED);
        verify(emailService).sendEmail(donationEntity.getDonor().getEmail(), "Your donation is fully requested!", null);

    }

    @Test
    void abandonRequest() {
        long donationId = 1L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity requesterEntity = UserEntity.builder().id(2L).build();
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(requesterEntity);

        donationEntity.getUserRequests().add(requesterEntity);

        when(donationRepository.findById(donationId))
                .thenReturn(Optional.of(donationEntity));

        //when
        sut.abandonRequest(donationId, loggedInUserMock);

        //then
        assertThat(donationEntity.getUserRequests()).doesNotContain(requesterEntity);
    }

    @Test
    void given_non_requester_user_when_abandon_request_then_throw_exception() {
        long donationId = 1L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity requesterEntity = UserEntity.builder().id(2L).build();
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(requesterEntity);

        when(donationRepository.findById(donationId))
                .thenReturn(Optional.of(donationEntity));

        //then
        assertThrows(BadRequestException.class, () -> sut.abandonRequest(donationId, loggedInUserMock));

    }

    @Test
    void given_requesting_user_and_fully_required_donation_when_abandonRequest_then_remove_user_and_set_status_to_available() {
        long donationId = 1L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity requesterEntity = UserEntity.builder().id(2L).build();
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(requesterEntity);

        donationEntity.getUserRequests().add(requesterEntity);
        donationEntity.setStatus(Status.FULLY_REQUESTED);

        when(donationRepository.findById(donationId))
                .thenReturn(Optional.of(donationEntity));

        //when
        sut.abandonRequest(donationId, loggedInUserMock);

        //then
        assertThat(donationEntity.getUserRequests()).doesNotContain(requesterEntity);
        assertThat(donationEntity.getStatus()).isEqualTo(Status.AVAILABLE);
    }

    @Test
    void given_is_and_user_details_when_giveDonation_then_transaction_is_created_and_email_is_sent() {
        //given
        long donationId = 1L;
        long receiverId = 3L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(userEntity);

        when(donationRepository.findById(donationId))
                .thenReturn(Optional.of(donationEntity));

        UserEntity receiverEntityMock = mock(UserEntity.class);
        when(userService.findEntityById(receiverId)).thenReturn(receiverEntityMock);

        TransactionEntity transactionEntityMock = mock(TransactionEntity.class);
        when(transactionService.save(donationId, receiverEntityMock)).thenReturn(transactionEntityMock);

        String receiverEmail = "give.donation.test@freecycle.com";
        when(receiverEntityMock.getEmail()).thenReturn(receiverEmail);

        //when
        sut.giveDonation(receiverId, donationId, loggedInUserMock);

        //then
        assertThat(donationEntity.getStatus()).isEqualTo(Status.DONATED);
        verify(transactionService).save(donationId, receiverEntityMock);
        verify(emailService).sendEmail(receiverEmail, "You received a donation!", null);
    }

    @Test
    void given_non_donor_user_when_give_donation_then_throw_exception() {
        //given
        long donationId = 1L;
        long receiverId = 3L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity donorMock = mock(UserEntity.class);
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(donorMock);
        when(donationRepository.findById(donationId))
                .thenReturn(Optional.of(donationEntity));

        //then
        assertThrows(ForbiddenException.class, () -> sut.giveDonation(receiverId, donationId, loggedInUserMock));
    }

    @Test
    void given_donated_object_when_give_donation_then_throw_exception() {
        //given
        long donationId = 1L;
        long receiverId = 3L;
        UserDetails loggedInUserMock = mock(UserDetails.class);
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(userEntity);

        donationEntity.setStatus(Status.DONATED);
        when(donationRepository.findById(donationId))
                .thenReturn(Optional.of(donationEntity));

        //then
        assertThrows(BadRequestException.class, () -> sut.giveDonation(receiverId, donationId, loggedInUserMock));
    }

    @Test
    void given_donationDTO_and_user_details_when_update_then_return_updated_dto() {
        //given
        UserDetails loggedInUserMock = mock(UserDetails.class);
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(userEntity);

        DonationDTO donationDTOMock = mock(DonationDTO.class);

        when(donationRepository.findById(donationDTOMock.getId()))
                .thenReturn(Optional.of(donationEntity));

        when(mapper.map(donationDTOMock.getCategory(), CategoryEntity.class))
                .thenReturn(categoryEntity);
        when(mapper.map(donationDTOMock.getArea(), AreaOfAvailabilityEntity.class))
                .thenReturn(areaEntity);

        when(mapper.map(donationEntity, DonationDTO.class))
                .thenReturn(donationDTO);

        //when
        DonationDTO actualDonationDTO = sut.update(donationDTOMock, loggedInUserMock);

        //then
        assertThat(actualDonationDTO).isSameAs(donationDTO);
    }

    @Test
    void given_donationDTO_and_wrong_credentials_when_update_then_throw_exception() {
        UserDetails loggedInUserMock = mock(UserDetails.class);
        UserEntity userEntityMock = mock(UserEntity.class);
        when(userService.findEntityByUserName(loggedInUserMock.getUsername()))
                .thenReturn(userEntityMock);
        DonationDTO donationDTOMock = mock(DonationDTO.class);

        when(donationRepository.findById(donationDTOMock.getId()))
                .thenReturn(Optional.of(donationEntity));

        //then
        assertThrows(ForbiddenException.class, () -> sut.update(donationDTOMock, loggedInUserMock));
    }


}