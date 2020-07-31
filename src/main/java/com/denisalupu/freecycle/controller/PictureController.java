package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.service.PictureStorageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("pictures")
@SecurityRequirement(name = "http_basic")
@AllArgsConstructor
public class PictureController {

    private final PictureStorageService pictureStorageService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestParam("file") MultipartFile file, @RequestParam("donationId") long donationId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pictureStorageService.store(file, donationId, userDetails);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPictureById(@PathVariable("id") long id) {
        return pictureStorageService.getBytesById(id);
    }
}
