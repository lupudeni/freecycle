package com.denisalupu.freecycle.domain.entity;

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
@Table(name = "areas_of_availability")
public class AreaOfAvailabilityEntity {
    //TODO:take this out of equals and hashcode?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    private String city;

    //TODO:take this out of equals and hashcode?

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "area")
    private List<DonationEntity> donations;
}
