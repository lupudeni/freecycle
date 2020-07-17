package com.denisalupu.freecycle.controller;

import com.denisalupu.freecycle.service.PictureStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("pictures")
public class PictureController {
    private final PictureStorageService pictureStorageService;

    //TODO handle ion ex in service
    //TODO add exception handlers
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestParam("file") MultipartFile file, @RequestParam("donationId") long donationId) throws IOException {
        pictureStorageService.store(file, donationId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPictureById(@PathVariable("id") long id) {
        return pictureStorageService.getBytesById(id);
    }
}
