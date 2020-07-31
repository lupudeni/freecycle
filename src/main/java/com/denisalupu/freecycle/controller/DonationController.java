package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.model.DonationDTO;
import com.denisalupu.freecycle.service.DonationService;
import com.denisalupu.freecycle.util.AuthenticationUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("donations")
@SecurityRequirement(name = "http_basic")
@AllArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DonationDTO create(@RequestBody DonationDTO donationDTO) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.create(donationDTO, userDetails);
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationDTO> getAllActiveDonations() {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.getAllActiveDonations(userDetails);
    }

    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationDTO> getAllHistory() {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.getAllHistory(userDetails);
    }

    @GetMapping("/requested")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationDTO> getAllActiveRequests() {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.getAllRequests(userDetails);
    }

    /**
     * Method used for the frontend option "request" -> "find donations":
     * Method  filters based on the donation objects with the status "AVAILABLE",
     * matching the 3 parameters passed.
     *
     * @param categoryId
     * @param areaId
     * @param title
     * @return
     */
    @GetMapping("/findDonations")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationDTO> findDonations(
            @RequestParam("categoryId") long categoryId,
            @RequestParam("areaId") long areaId,
            @RequestParam("title") String title) {

        return donationService.findDonations(categoryId, areaId, title);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public DonationDTO updateDonation(@RequestBody DonationDTO donationDTO) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.update(donationDTO, userDetails);
    }

    @PutMapping("/request/{donationId}")
    @ResponseStatus(HttpStatus.OK)
    public DonationDTO requestDonation(@PathVariable ("donationId") long donationId){
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.requestDonation(donationId, userDetails);
    }

    @PutMapping("/abandonRequest/{donationId}")
    @ResponseStatus(HttpStatus.OK)
    public void abandonRequest(@PathVariable ("donationId") long donationId) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        donationService.abandonRequest(donationId, userDetails);
    }

    @PutMapping("/giveDonation")
    @ResponseStatus(HttpStatus.OK)
    public void giveDonation(@RequestParam("receiverId") long receiverId,
                             @RequestParam("donationId") long donationId) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        donationService.giveDonation(receiverId, donationId, userDetails);
    }

    //TODO add remove donation

}
