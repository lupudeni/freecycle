package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.Status;
import com.denisalupu.freecycle.domain.model.DonationDTO;
import com.denisalupu.freecycle.domain.model.RequestDTO;
import com.denisalupu.freecycle.service.DonationService;
import com.denisalupu.freecycle.util.AuthenticationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("donations")
@AllArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DonationDTO create(@RequestBody DonationDTO donationDTO) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.create(donationDTO, userDetails);
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
     * <p>
     * frontend option "donate" -> "past donations":
     * - DONATED
     * <p>
     * frontend option "request" -> "active requests"
     * Note that the user has to be in the requests set of the following donations:
     * - FULLY_REQUESTED
     * <p>
     * frontend option "request" -> "past requests"
     * Note that the user has to be in the requests set of the following donations:
     * - DONATED
     *
     * @param statuses
     * @return
     */
    //useless
    //TODO: test in postman with multiple status when having multiple donations
    @GetMapping("/getByStatus")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationDTO> getAllByStatus(@RequestParam("statuses") Status[] statuses) {
        return donationService.findAllByStatus(statuses);
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

    @PutMapping("/request")
    @ResponseStatus(HttpStatus.OK)
    public DonationDTO requestDonation(@RequestBody RequestDTO requestDTO) {
        return donationService.requestDonation(requestDTO);
    }

    @PutMapping("/abandonRequest")
    @ResponseStatus(HttpStatus.OK)
    public void abandonRequest(@RequestBody RequestDTO requestDTO) {
        donationService.abandonRequest(requestDTO);
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
