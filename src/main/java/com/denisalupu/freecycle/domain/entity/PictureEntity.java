package com.denisalupu.freecycle.domain.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donation_pictures")
public class PictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_id")
    private DonationEntity donation;
}
