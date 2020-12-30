package cz.cvut.bikesharingproject.model;

import cz.cvut.bikesharingproject.model.enums.Role;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PERSON_TYPE")
public abstract class Person extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private String surname;

    @Basic(optional = false)
    @Column(nullable = false)
    private String email;

    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    @Basic(optional = false)
    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;
}
