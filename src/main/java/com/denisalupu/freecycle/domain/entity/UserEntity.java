package com.denisalupu.freecycle.domain.entity;

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

    @OneToMany(mappedBy = "donor")
    private List<DonationEntity> donatedObjects;

    @OneToMany(mappedBy = "receiver")
    private List<DonationEntity> receivedObjects;

    /**
     * Resources and ideas for the Many to Many relationship:
     * https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
     */

    @ManyToMany(mappedBy = "userRequests")
    private Set<DonationEntity> requestedDonations;

}

