package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.PictureEntity;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
import com.denisalupu.freecycle.exception.GeneralServerException;
import com.denisalupu.freecycle.repository.PictureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class PictureStorageService {

    private final PictureRepository pictureRepository;
    private final DonationService donationService;

    public void store(MultipartFile file, long donationId) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new GeneralServerException("Upload failed");
        }
        DonationEntity donationEntity = donationService.findEntityById(donationId);
        PictureEntity pictureEntity = PictureEntity.builder()
                .picture(bytes)
                .donation(donationEntity).build();
        pictureRepository.save(pictureEntity);
    }

    public byte[] getBytesById(long id) {
        PictureEntity pictureEntity = pictureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No picture with id '" + id + "' found"));
        return pictureEntity.getPicture();
    }
}
