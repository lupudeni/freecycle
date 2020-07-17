package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.model.*;
import com.denisalupu.freecycle.service.DonationService;
import com.denisalupu.freecycle.utils.SortOrder;
import com.denisalupu.freecycle.utils.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("donations")
@AllArgsConstructor
public class DonationController {
    private final DonationService donationService;

    //TODO add status of available from here
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DonationDTO create(@RequestBody DonationDTO donationDTO) {
        return donationService.create(donationDTO);
    }

    //useless
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DonationDTO findById(@PathVariable("id") long id) {
        return donationService.findById(id);
    }


    /**
     * According to the position of the user and the option selected by them in the frontend,
     * the application will provide a view of Donation objects with different statuses;
     * Statuses apply as follows:
     * frontend option "donate" -> "active donations":
     * - ACTIVE
     * - FULLY_REQUESTED
     * - AWAITING_CONFIRMATION
     * - IN_PROGRESS
     * <p>
     * frontend option "donate" -> "past donations":
     * - DONATED
     * <p>
     * frontend option "request" -> "active requests"
     * Note that the user has to be in the requests set of the following donations:
     * - FULLY_REQUESTED
     * - AWAITING_CONFIRMATION
     * - IN_PROGRESS
     * <p>
     * frontend option "request" -> "past requests"
     * Note that the user has to be in the requests set of the following donations:
     * - DONATED
     *
     * @param statuses
     * @return
     */

    @GetMapping("/getByStatus")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationDTO> getAllByStatus(@RequestParam("statuses") Status[] statuses) {
        return donationService.findAllByStatus(statuses);
    }

    /**
     * Method used for the frontend option "request" -> "find donations":
     * Method  filters based on the donation objects with the status "AVAILABLE",
     * matching the 3 parameters passed.
     *
     * @param category
     * @param area
     * @param title
     * @return
     */

    //TODO find out how to check this in POSTMAN
    @GetMapping("/findDonations")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationDTO> findDonations(
            @RequestParam("category") CategoryDTO category,
            @RequestParam("area") AreaOfAvailabilityDTO area,
            @RequestParam("title") String title) {

        return donationService.findDonations(category, area, title);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public DonationDTO updateDonation(@RequestBody DonationDTO donationDTO) {
        return donationService.update(donationDTO);
    }

    @PutMapping("/request")
    @ResponseStatus(HttpStatus.OK)
    public DonationDTO requestDonation(@RequestBody RequestDTO requestDTO) {
       return donationService.requestDonation(requestDTO);
    }

    @PutMapping("/abandonRequest")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abandonRequest(@RequestBody RequestDTO requestDTO) {
        donationService.abandonRequest(requestDTO);
    }

}
