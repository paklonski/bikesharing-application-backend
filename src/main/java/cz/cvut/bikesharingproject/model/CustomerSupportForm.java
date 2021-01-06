package cz.cvut.bikesharingproject.model;

import cz.cvut.bikesharingproject.model.enums.CustomerSupportFormState;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class CustomerSupportForm extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerSupportFormState status;

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime timeOfCreation;

//    @ManyToMany
//    @JoinTable(
//            name = "supportForms_users",
//            joinColumns = @JoinColumn(name = "supportForm_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false))
//    private Set<User> participants;
}
