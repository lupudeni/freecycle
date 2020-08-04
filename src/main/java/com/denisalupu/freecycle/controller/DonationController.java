package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.domain.model.DonationDTO;
import com.denisalupu.freecycle.service.DonationService;
import com.denisalupu.freecycle.util.AuthenticationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Creates a donation. Returns the created DonationDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a donation"),
            @ApiResponse(responseCode = "400", description = "Invalid body passed for donation"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DonationDTO create(@RequestBody DonationDTO donationDTO) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.create(donationDTO, userDetails);
    }

    @Operation(summary = "Returns all donations with status \"AVAILABLE\" and \"FULLY_REQUESTED\" posted by the logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned a list of donations"),
            @ApiResponse(responseCode = "400", description = "Invalid body passed for donation"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials")
    })
    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationDTO> getAllActiveDonations() {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.getAllActiveDonations(userDetails);
    }

    @Operation(summary = "Returns a list of all donations with status \"DONATED\" posted by the logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned a list of donations"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials")
    })
    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationDTO> getAllHistory() {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.getAllHistory(userDetails);
    }

    @Operation(summary = "Returns a list of all donations requested by the logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned a list of donations"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials")
    })
    @GetMapping("/requested")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationDTO> getAllActiveRequests() {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.getAllRequests(userDetails);
    }

    @Operation(summary = "Returns a list of ACTIVE DonationDTOs matching the given params.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned a list of donations"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters passed for donation search"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationDTO> findDonations(
            @RequestParam("categoryId") long categoryId,
            @RequestParam("areaId") long areaId,
            @RequestParam("title") String title) {

        return donationService.findDonations(categoryId, areaId, title);
    }

    @Operation(summary = "Updates a donation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the donations"),
            @ApiResponse(responseCode = "400", description = "Invalid body passed for updating donation"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public DonationDTO updateDonation(@RequestBody DonationDTO donationDTO) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.update(donationDTO, userDetails);
    }

    @Operation(summary = "Makes a request for the specified donation by the logged in user. ",
            description = "The user is added in the user requests list of the donation." +
            "If the donation is requested by the max limit number of users, then the status is changed to \"FULLY_REQUESTED\" " +
            "and an email is sent to the donor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully requested the donations"),
            @ApiResponse(responseCode = "400", description = "Invalid path passed for requesting donation"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PatchMapping("/request/{donationId}")
    @ResponseStatus(HttpStatus.OK)
    public DonationDTO requestDonation(@PathVariable ("donationId") long donationId){
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        return donationService.requestDonation(donationId, userDetails);
    }

    @Operation(summary = "Cancels the request of the specified donation by the logged in user.",
            description = "The user is taken out of the list of user requests of the donation. " +
            "If the donation's status was previously set to \"FULLY_REQUESTED\", it is now changed back to \"ACTIVE\"")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully canceled the request for the donations"),
            @ApiResponse(responseCode = "400", description = "Invalid path passed for canceling request"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PatchMapping("/request/cancel/{donationId}")
    @ResponseStatus(HttpStatus.OK)
    public void abandonRequest(@PathVariable ("donationId") long donationId) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        donationService.abandonRequest(donationId, userDetails);
    }

    @Operation(summary = "Gives the donation specified by the logged in user to the user identified by the \"receiverId\".",
            description = "The donation's status is set to \"DONATED\" and an email is sent to the receiving user with contact data. " +
            "The donation will no longer appear in searches and in requested donations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully gave away the donations"),
            @ApiResponse(responseCode = "400", description = "Invalid params passed for giving away donation"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PatchMapping("/give")
    @ResponseStatus(HttpStatus.OK)
    public void giveDonation(@RequestParam("receiverId") long receiverId,
                             @RequestParam("donationId") long donationId) {
        UserDetails userDetails = AuthenticationUtils.getLoggedInUser();
        donationService.giveDonation(receiverId, donationId, userDetails);
    }

    //TODO add remove donation

}
