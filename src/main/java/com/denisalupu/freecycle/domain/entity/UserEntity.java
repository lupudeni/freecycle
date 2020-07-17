package com.denisalupu.freecycle.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private String email;

    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "donor")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DonationEntity> donatedObjects;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DonationEntity> receivedObjects;

    /**
     * Resources and ideas for the Many to Many relationship:
     * https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
     */

    @ManyToMany(mappedBy = "userRequests")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DonationEntity> requestedDonations;

}

