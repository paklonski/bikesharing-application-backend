package cz.cvut.bikesharingproject.model;

import cz.cvut.bikesharingproject.model.enums.CSFStatus;
import cz.cvut.bikesharingproject.model.enums.CSFType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class CustomerSupportForm extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private CSFType type;

    @Enumerated(EnumType.STRING)
    private CSFStatus status;

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime timeOfCreation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @ManyToMany
    @JoinTable(
            name = "supportForm_responsiblePerson",
            joinColumns = @JoinColumn(name = "supportForm_id"),
            inverseJoinColumns = @JoinColumn(name = "responsiblePerson_id", nullable = false))
    private Set<Admin> responsiblePersons;
}
