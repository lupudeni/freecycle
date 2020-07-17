package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.DonationEntity;
import com.denisalupu.freecycle.domain.entity.PictureEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.exception.EntityNotFoundException;
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

    public void store(MultipartFile file, long donationId) throws IOException {
        byte[] bytes = file.getBytes();
        DonationEntity donationEntity = donationService.findEntityById(donationId);
        PictureEntity pictureEntity = PictureEntity.builder()
                .picture(bytes)
                .donation(donationEntity).build();
        pictureRepository.save(pictureEntity);
    }

    //TODO delete all find by ids ca deja exista in jpa rep
    public byte[] getBytesById(long id) {
        PictureEntity pictureEntity = pictureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No picture with id '" + id + "' found"));
        return pictureEntity.getPicture();
    }
}
