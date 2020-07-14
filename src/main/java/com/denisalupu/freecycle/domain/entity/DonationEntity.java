package com.denisalupu.freecycle.domain.entity;

import com.denisalupu.freecycle.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donations")
public class DonationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private AreaOfAvailabilityEntity area;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private UserEntity donor;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "donation")
    private List<PictureEntity> pictures;

}
