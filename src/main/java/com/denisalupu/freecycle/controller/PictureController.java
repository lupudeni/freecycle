package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.service.PictureStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("donations/pictures")
@SecurityRequirement(name = "http_basic")
@AllArgsConstructor
public class PictureController {

    private final PictureStorageService pictureStorageService;

    //todo: make sure only certain formats can be uploaded

    @Operation(summary = "Uploads a picture for a specific donation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully uploaded a new picture"),
            @ApiResponse(responseCode = "400", description = "Invalid param passed for uploading picture"),
            @ApiResponse(responseCode = "401", description = "Unauthorised login credentials")
    })
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestParam("file") MultipartFile file, @RequestParam("donationId") long donationId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pictureStorageService.store(file, donationId, userDetails);
    }

    @Operation(summary = "Retrieves a picture for a specific donation by the picture id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully retrieved a picture"),
            @ApiResponse(responseCode = "400", description = "Invalid path passed for retrieving picture"),
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPictureById(@PathVariable("id") long id) {
        return pictureStorageService.getBytesById(id);
    }

    //todo: change the above method to retrieve pictures based on the donation id, return a list of photos
}
