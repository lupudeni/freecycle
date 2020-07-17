package com.denisalupu.freecycle.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "areas_of_availability")
public class AreaOfAvailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    private String city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "area")
    @ToString.Exclude
    private List<DonationEntity> donations;
}
