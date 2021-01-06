package cz.cvut.bikesharingproject.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.cvut.bikesharingproject.model.enums.Role;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "USERS")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
})
public class User extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean blocked;

    @Basic(optional = false)
    @Column(name = "username", nullable = false)
    private String username;

    @Basic(optional = false)
    @Column(name = "password", nullable = false)
    private String password;

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "surname", nullable = false)
    private String surname;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @Basic(optional = false)
    @Column(name = "email", nullable = false)
    private String email;

    @Basic(optional = false)
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Basic(optional = false)
    @Column(name = "balance", nullable = false, columnDefinition = "NUMERIC(7,2)")
    private BigDecimal balance;

//    @ManyToMany(mappedBy = "participants")
//    private List<CustomerSupportForm> supportFormsHistory;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> rentalHistory;
}
