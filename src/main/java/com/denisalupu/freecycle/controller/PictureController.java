package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.service.PictureStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("pictures")
public class PictureController {

    private final PictureStorageService pictureStorageService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestParam("file") MultipartFile file, @RequestParam("donationId") long donationId) {
        pictureStorageService.store(file, donationId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPictureById(@PathVariable("id") long id) {
        return pictureStorageService.getBytesById(id);
    }
}
