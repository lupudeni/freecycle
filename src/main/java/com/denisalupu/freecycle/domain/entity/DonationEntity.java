package com.denisalupu.freecycle.domain.entity;

import com.denisalupu.freecycle.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

//TODO: modify to have donation type-> avoid duplication

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donations")
//@EntityListeners(AuditingEntityListener.class)
public class DonationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private AreaOfAvailabilityEntity area;

    @Enumerated(EnumType.STRING)
    private Status status;

    //maybe in user there can be a field: donor for and receiver for
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id")
    private UserEntity donor;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "receiver_id")
//    private UserEntity receiver;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "donation")
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

//    @LastModifiedDate
//    private LocalDateTime lastModified;


}
