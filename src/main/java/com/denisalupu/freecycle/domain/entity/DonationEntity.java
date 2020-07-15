package com.denisalupu.freecycle.domain.entity;

import com.denisalupu.freecycle.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


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

    /**
     * Resources and ideas for the Many to Many relationship:
     * https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
     */

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "requests",
            joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> userRequests;

}
